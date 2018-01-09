package com.dupleit.kotlin.kotlinapp.BackgroundService

/**
 * Created by android on 6/1/18.
 */
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Binder
import android.util.Log
import android.widget.Toast
import com.dupleit.kotlin.kotlinapp.UserServerData
import com.dupleit.kotlin.kotlinapp.utils.dateNow
import com.dupleit.kotlin.kotlinapp.utils.timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class UserlocationUpdateService : Service() {
    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
    lateinit var fbAuth: FirebaseAuth
    internal var currentLat = "0.0"
    internal var currentLong = "0.0"
    private var timer: Timer? = null
    private var gpsTracker: GPSTracker? = null
    var count: Int = 0
    lateinit var userPref: SharedPreferences

    override fun onBind(intent: Intent?): Binder? = null

    override fun onCreate() {
        super.onCreate()

        userPref = this.getSharedPreferences("journey",0)

        fbAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        mMessageReference = FirebaseDatabase.getInstance().getReference(fbAuth.currentUser?.uid).child("journey")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this@UserlocationUpdateService, "Service Start",Toast.LENGTH_LONG).show()
        gpsTracker = GPSTracker(this@UserlocationUpdateService)

        val userCount = userPref.getInt("journeyCount",1)
        mMessageReference!!.child(dateNow()+"-"+userCount).child("journeyDetail").child("journeyStart").setValue(gpsTracker?.getLatitude().toString()+","+gpsTracker?.getLongitude().toString())
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                gpsTrackerCredentials().execute()
            }
        }, 0, (10 * 1000).toLong())

        return Service.START_STICKY
    }


    private inner class gpsTrackerCredentials : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg arg0: String?): String? {
            Log.d("Background","I am running lat"+gpsTracker?.getLatitude()+"..... Long"+gpsTracker?.getLongitude())
            return "Hello"
        }

        override fun onPostExecute(unused: String) {
            super.onPostExecute(unused)
            DriverUpdateDetails()
        }
    }


    private fun DriverUpdateDetails() {
        try {
            currentLat = java.lang.Double.toString(gpsTracker!!.getLocation()!!.latitude)
            currentLong = java.lang.Double.toString(gpsTracker!!.getLocation()!!.longitude)
            var userCount = userPref.getInt("journeyCount",1)
            val user = UserServerData(currentLat.toDouble(), currentLong.toDouble(), timestamp())
            mMessageReference!!.child(dateNow()+"-"+userCount).child("route").child(""+(count++)).setValue(user)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Tracking Stop ",Toast.LENGTH_LONG).show()
        println("-------------Service Stop-----------")

        try {
            timer!!.cancel()
            //var userStop = userPref.getInt("journeyNo",1)
            //userStop = userStop++
            var userCount = userPref.getInt("journeyCount",1)
            mMessageReference!!.child(dateNow()+"-"+userCount).child("journeyDetail").child("journeyStop").setValue(gpsTracker?.getLatitude().toString()+","+gpsTracker?.getLongitude().toString())
            val editor = userPref.edit()
            userCount += 1
            editor.putInt("journeyCount", userCount)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}