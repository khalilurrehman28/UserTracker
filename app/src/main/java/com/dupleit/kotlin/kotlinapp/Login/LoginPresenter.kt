package com.dupleit.kotlin.kotlinapp.Login

import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.dupleit.kotlin.kotlinapp.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

/**
 * Created by android on 8/1/18.
 */
class LoginPresenter : LoginCallback {
    var context: Context? = null
    lateinit var fbAuth: FirebaseAuth

    override fun initializeCallback(loginActivity: LoginActivity) {
        context = loginActivity
        fbAuth = FirebaseAuth.getInstance()
    }

    override fun testandregister(email: AutoCompleteTextView?, password: EditText?) {
        if (!isEmailValid(email?.text.toString())){
            email!!.setError("Wrong Email")
            return
        }else if (isPasswordBlank(password?.text.toString())) {
            password!!.setError("Password is blank")
            return
        }else if ((password?.text.toString().length)<6){
            password!!.setError("Password length is small")
            return
        }else{
            registerUser(email?.text.toString(),password?.text.toString())
        }
    }

    private fun registerUser(userEmail: String, userPassword: String) {
        fbAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(OnCompleteListener {
            val intent: Intent = Intent(context, MainActivity::class.java)
            context?.startActivity(intent)
        }).addOnFailureListener(OnFailureListener {
            fbAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(OnCompleteListener {
                val intent: Intent = Intent(context, MainActivity::class.java)
                context?.startActivity(intent)
            })
        })
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
