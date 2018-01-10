package com.dupleit.kotlin.kotlinapp.userHistoryList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.dupleit.kotlin.kotlinapp.R
import kotlinx.android.synthetic.main.activity_user_history_list.*

class userHistoryList : AppCompatActivity() {

    lateinit var presenter:userHistoryPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_history_list)
        presenter = userHistoryPresenter()
        presenter.initializeCallback(this)

       // userList.layoutManager = Lin

    }


}
