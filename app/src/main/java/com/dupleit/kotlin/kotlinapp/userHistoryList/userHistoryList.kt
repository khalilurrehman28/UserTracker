package com.dupleit.kotlin.kotlinapp.userHistoryList
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.routeMap.MapsActivity
import com.dupleit.kotlin.kotlinapp.userHistoryList.adapter.userListRecycler
import com.dupleit.kotlin.kotlinapp.utils.RecyclerTouchListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_history_list.*
class userHistoryList : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var presenter:userHistoryPresenter
    lateinit var userListArray: ArrayList<String>
    lateinit var mFirebaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var fbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_history_list)
        var TAG: String = "userHistoryList"
        database = FirebaseDatabase.getInstance()
        mFirebaseReference = database.getReference()
        presenter = userHistoryPresenter()
        presenter.initializeCallback(this)
        fbAuth = FirebaseAuth.getInstance()
        userListArray = ArrayList()
        linearLayoutManager = LinearLayoutManager(this)
        userList.layoutManager = linearLayoutManager
        val adapter = userListRecycler(userListArray)
        userList.adapter = adapter
        val userDetails = mFirebaseReference.child(fbAuth.currentUser?.uid).child("journey")
        userList.addOnItemTouchListener(RecyclerTouchListener(applicationContext, userList, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(applicationContext, userListArray.get(position), Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, MapsActivity::class.java).putExtra("mappoints", userListArray.get(position)))
            }
            override fun onLongClick(view: View?, position: Int) {
            }
        }))
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildName: String?) {
                userListArray.add(dataSnapshot!!.key)
                Log.e(TAG, "onChildAdded:" + dataSnapshot.key)
                adapter.notifyDataSetChanged()
                Log.e(TAG, "onChildChanged:" + dataSnapshot)
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot?, previousChildName: String?) {
                Log.e(TAG, "onChildChanged:" + dataSnapshot!!.key)
                // A message has changed
                //val message = dataSnapshot.getValue(Message::class.java)
            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot!!.key)
                // A message has been removed
                // val message = dataSnapshot.getValue(Message::class.java)
            }
            override fun onChildMoved(dataSnapshot: DataSnapshot?, previousChildName: String?) {
                Log.e(TAG, "onChildMoved:" + dataSnapshot!!.key)
                // A message has changed position
                //val message = dataSnapshot.getValue(Message::class.java)
            }
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.e(TAG, "postMessages:onCancelled", databaseError!!.toException())
            }
        }
        userDetails.addChildEventListener(childEventListener)
    }
}
