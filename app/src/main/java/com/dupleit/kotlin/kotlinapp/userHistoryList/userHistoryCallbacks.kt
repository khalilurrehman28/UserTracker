package com.dupleit.kotlin.kotlinapp.userHistoryList

import android.support.v7.widget.RecyclerView

/**
 * Created by android on 10/1/18.
 */
interface userHistoryCallbacks {
    fun initializeCallback(userHistoryList: userHistoryList)
    fun addHistoryRoutes(key: String?)
    fun addRecyclerView(userList: RecyclerView?)

}