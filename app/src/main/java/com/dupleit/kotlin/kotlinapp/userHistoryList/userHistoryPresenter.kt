package com.dupleit.kotlin.kotlinapp.userHistoryList
import android.content.Context
import android.support.v7.widget.RecyclerView
/**
 * Created by android on 10/1/18.
 */
class userHistoryPresenter: userHistoryCallbacks {
    lateinit var userListView: RecyclerView
    lateinit var context:Context
    override fun initializeCallback(userHistoryList: userHistoryList) {
        context=userHistoryList
    }
    override fun addHistoryRoutes(key: String?) {
    }
    override fun addRecyclerView(userList: RecyclerView?) {
        userListView = userList!!
    }
}
