package com.dupleit.kotlin.kotlinapp.Login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.dupleit.kotlin.kotlinapp.MainActivity
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.R.id.*
import com.dupleit.kotlin.kotlinapp.Register.RegisterActivity
import com.dupleit.kotlin.kotlinapp.Register.model.userProfileModel
import com.dupleit.kotlin.kotlinapp.login2
import com.dupleit.kotlin.kotlinapp.utils.PreferenceManager
import com.dupleit.kotlin.kotlinapp.utils.checkInternetState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

/**
 * Created by android on 8/1/18.
 */
class LoginPresenter : LoginCallback {


    var context: Context? = null
    lateinit var fbAuth: FirebaseAuth
    lateinit var activityLogin: LoginActivity
    override fun initializeCallback(loginActivity: LoginActivity) {
        context = loginActivity
        fbAuth = FirebaseAuth.getInstance()
        activityLogin = loginActivity
    }

    override fun testandregister(userEmail: EditText?, userPassword: EditText?) {
        if (!isEmailValid(userEmail?.text.toString())){
            userEmail!!.error = context?.getString(R.string.wrongemail)
            return
        }else if (isPasswordBlank(userPassword?.text.toString())) {
            userPassword!!.error ="Password is blank"
            return
        }else if ((userPassword?.text.toString().length)<6){
            userPassword!!.error = "Password length is small"
            return
        }else{
            registerUser(userEmail?.text.toString(),userPassword?.text.toString())
        }
    }

    override fun checkLoginUser() {
        if(fbAuth.currentUser!=null){
            if (fbAuth.currentUser!!.uid.isNotEmpty()){
               // val intent: Intent = Intent(context, MainActivity::class.java)
                val i = Intent(context, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context?.startActivity(i)
            }
        }
    }

    private fun registerUser(userEmail: String, userPassword: String) {

        activityLogin.showProgressbar()
        if (!checkInternetState.getInstance(context).isOnline) run {
            activityLogin.showSnackbar("Please check your internet connection.")
            activityLogin.hideProgressbar()
        }else {
            fbAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    //Registration OK
                    val i = Intent(context, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context?.startActivity(i)
                    activityLogin.hideProgressbar()

                    activityLogin.showSnackbar("Login Success")
                } else {

                    Log.e("firebaseUser", " already exist")
                    //Registration error
                    activityLogin.showSnackbar("Email not exist")
                    activityLogin.hideProgressbar()
                }
            }
        }
    }

    private fun isPasswordBlank(password: String): Boolean {
        return (if(password.length==0) true else false)
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
