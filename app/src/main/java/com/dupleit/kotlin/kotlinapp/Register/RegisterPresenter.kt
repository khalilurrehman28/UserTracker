package com.dupleit.kotlin.kotlinapp.Register

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import com.dupleit.kotlin.kotlinapp.Login.LoginActivity
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.Register.model.userProfileModel
import com.dupleit.kotlin.kotlinapp.utils.PreferenceManager
import com.dupleit.kotlin.kotlinapp.utils.checkInternetState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

/**
 * Created by mandeep on 1/11/18.
 */
class RegisterPresenter : RegisterCallback {

    var context: Context? = null
    lateinit var fbAuth: FirebaseAuth
    lateinit var activityReg:RegisterActivity
    private var database: FirebaseDatabase? = null
    private var mFirebaseReference: DatabaseReference? = null

    override fun initializeCallback(registerActivity: RegisterActivity) {
        context = registerActivity
        fbAuth = FirebaseAuth.getInstance()
        activityReg = registerActivity
        database = FirebaseDatabase.getInstance()
        mFirebaseReference = database!!.reference
    }

    override fun validateData(userName: EditText?, userMobile: EditText?, userEmail: EditText?, password: EditText?) {
        if (userName?.text.isNullOrEmpty()) {
            userName?.error = "Please Enter Your Name"
            return
        }
        if (userMobile?.text.isNullOrEmpty()) {
            userMobile?.error = "Please Enter Your Mobile No."
            return
        }
        if ((userMobile?.text.toString().length)!=10) {
            userMobile?.error = "10 Digit Required"
            return
        }
        if (!isEmailValid(userEmail?.text.toString())){
            userEmail!!.error = context?.getString(R.string.wrongemail)
            return
        }
        if (password?.text.isNullOrEmpty()) {
            password?.error = "Please Enter Your Password"
            return
        }
        if ((password?.text.toString().length)<6){
            password?.error = "Minimum 6 Digit Required"
            return
        }

        registerUser(userName?.text.toString(),userMobile?.text.toString(),
                userEmail?.text.toString(),password?.text.toString())

    }

    private fun registerUser(userName: String, userMobile: String, userEmail: String, password: String) {
        activityReg.showProgressbar()
        if (!checkInternetState.getInstance(context).isOnline) run {
            activityReg.showSnackbar("Please check your internet connection.")
            activityReg.hideProgressbar()
        }else {
            fbAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    //Registration OK
                    val firebaseUser = this.fbAuth.currentUser!!
                    val email = firebaseUser.email
                    Log.e("firebaseUser", " success" + firebaseUser.email)
                    if (email != null && firebaseUser.uid!=null) {

                        val hitData = userProfileModel(userName, userMobile.toLong(),email,firebaseUser.uid)
                        mFirebaseReference!!.child(firebaseUser.uid).child("userProfile").setValue(hitData)
                                .addOnCompleteListener(OnCompleteListener {
                                    val i = Intent(context, LoginActivity::class.java)
                                    PreferenceManager(context).saveUserMobile(userMobile.toLong())//to save mobile no. into sharedPref
                                    PreferenceManager(context).saveAuthUID(firebaseUser.uid)//to save firebase auth User UID into sharedPref (like trv72ki5rXT8j7XvbpBpalsos5n1)
                                    PreferenceManager(context).saveUserEmail(email)//to save firebase auth UserEmail sharedPref (like mandeeps@gmail.com)
                                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context!!.startActivity(i)
                                    activityReg.showSnackbar("Registration Success")
                                    activityReg.hideProgressbar()

                                }).addOnFailureListener(OnFailureListener {
                            activityReg.showSnackbar("Registration Failed")
                            activityReg.hideProgressbar()


                        })

                    }else{
                        activityReg.showSnackbar("Something went wrong")
                        activityReg.hideProgressbar()
                    }

                } else {

                    Log.e("firebaseUser", " already exist")
                    //Registration error
                    activityReg.showSnackbar("Email already exist")
                    activityReg.hideProgressbar()
                }
            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}