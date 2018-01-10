package com.dupleit.kotlin.kotlinapp.userHistoryList.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dupleit.kotlin.kotlinapp.R
import kotlinx.android.synthetic.main.user_history_list.view.*

/**
 * Created by android on 10/1/18.
 */
class userListRecycler(val userList: ArrayList<String>) : RecyclerView.Adapter<userListRecycler.userHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: userHolder?, position: Int) {
        val userName = userList[position]
        holder?.userName?.text = userName
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): userHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.user_history_list, parent, false)
        return userHolder(view)
    }

    class userHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val userName = itemview.userName
    }

}