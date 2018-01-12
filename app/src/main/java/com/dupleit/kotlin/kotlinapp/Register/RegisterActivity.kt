package com.dupleit.kotlin.kotlinapp.Register

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.dupleit.kotlin.kotlinapp.Login.LoginActivity
import com.dupleit.kotlin.kotlinapp.MainActivity
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.Register.activityCallbacks.iActivityCallbacks
import com.dupleit.kotlin.kotlinapp.utils.checkInternetState.context
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), iActivityCallbacks {
    lateinit var presenter: RegisterPresenter
    private lateinit var snackbar:Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val customFont = Typeface.createFromAsset(assets, "fonts/LatoLight.ttf")
        val customFont1 = Typeface.createFromAsset(assets, "fonts/LatoRegular.ttf")
        userName.typeface =customFont
        userMobile.typeface = customFont
        userEmail.typeface = customFont
        password.typeface = customFont
        alreadyAccount.typeface = customFont
        buttonSignup.typeface = customFont1
        presenter = RegisterPresenter()
        presenter.initializeCallback(this)

        buttonSignup.setOnClickListener {
            presenter.validateData(userName,userMobile,userEmail,password)
        }

        alreadyAccount.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
            finish()
        }

    }
    override fun showProgressbar() {
        progressbar.visibility =View.VISIBLE
    }

    override fun hideProgressbar() {
        progressbar.visibility =View.INVISIBLE
    }

    override fun showSnackbar(message: String) {
        snackbar = Snackbar.make(frame, ""+message, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        snackBarView.setBackgroundColor(resources.getColor(R.color.black))
        snackbar.show()
    }



}
