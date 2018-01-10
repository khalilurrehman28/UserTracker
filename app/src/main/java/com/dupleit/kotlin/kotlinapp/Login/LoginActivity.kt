package com.dupleit.kotlin.kotlinapp.Login

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dupleit.kotlin.kotlinapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter()

        presenter.initializeCallback(this)

        presenter.checkLoginUser()

        email_sign_in_button.setOnClickListener({ v -> presenter.testandregister(email,password) })

    }


}

