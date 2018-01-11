package com.dupleit.kotlin.kotlinapp.userHistoryList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.userHistoryList.adapter.userListRecycler
import kotlinx.android.synthetic.main.activity_user_history_list.*

class userHistoryList : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var presenter:userHistoryPresenter
    lateinit var userListArray: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_history_list)

        presenter = userHistoryPresenter()
        presenter.initializeCallback(this)

        userListArray = ArrayList()

        linearLayoutManager = LinearLayoutManager(this)
        userList.layoutManager = linearLayoutManager

        val adapter = userListRecycler(userListArray)

        userList.adapter = adapter


    }


}
