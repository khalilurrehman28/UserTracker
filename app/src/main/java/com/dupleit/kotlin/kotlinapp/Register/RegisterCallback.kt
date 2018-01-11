package com.dupleit.kotlin.kotlinapp.Register

import android.widget.AutoCompleteTextView
import android.widget.EditText

/**
 * Created by mandeep on 1/11/18.
 */
interface RegisterCallback {
    fun initializeCallback(registerActivity: RegisterActivity)
    fun validateData(userName: EditText?,userMobile: EditText?,userEmail:EditText?, password: EditText?)
}