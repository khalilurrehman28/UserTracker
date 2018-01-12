package com.dupleit.kotlin.kotlinapp.Login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dupleit.kotlin.kotlinapp.Login.activityCallbacks.iLoginActivityCallbacks
import com.dupleit.kotlin.kotlinapp.MainActivity
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.Register.RegisterActivity
import com.dupleit.kotlin.kotlinapp.utils.checkInternetState.context
import kotlinx.android.synthetic.main.activity_Login.*

class LoginActivity : AppCompatActivity(),iLoginActivityCallbacks {

    lateinit var presenter: LoginPresenter
    private lateinit var snackbar: Snackbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_Login)
        val customFont = Typeface.createFromAsset(assets, "fonts/LatoLight.ttf")
        val customFont1 = Typeface.createFromAsset(assets, "fonts/LatoRegular.ttf")
        userEmail.typeface = customFont
        userPassword.typeface = customFont
        createNewAccount.typeface = customFont
        buttonLogin.typeface =customFont1
        presenter = LoginPresenter()
        presenter.initializeCallback(this)

        presenter.checkLoginUser()

        buttonLogin.setOnClickListener({ v -> presenter.testandregister(userEmail,userPassword) })

        createNewAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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

