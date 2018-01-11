package com.dupleit.kotlin.kotlinapp.Login

import android.widget.AutoCompleteTextView
import android.widget.EditText

/**
 * Created by android on 8/1/18.
 */
interface LoginCallback {
    fun initializeCallback(loginActivity: LoginActivity)
    fun testandregister(userEmail: EditText?, userPassword: EditText?)
    fun checkLoginUser()
}