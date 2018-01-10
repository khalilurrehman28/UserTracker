package com.dupleit.kotlin.kotlinapp.userHistoryList

import android.content.Context

/**
 * Created by android on 10/1/18.
 */
class userHistoryPresenter: userHistoryCallbacks {
    lateinit var context:Context
    override fun initializeCallback(userHistoryList: userHistoryList) {
        context=userHistoryList
    }


}