package com.dupleit.kotlin.kotlinapp.BackgroundService

/**
 * Created by android on 6/1/18.
 */
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.dupleit.kotlin.kotlinapp.UserServerData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class UserlocationUpdateService : Service() {

    private val status = false
    ////////////////////Location////////////////////
    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
    lateinit var fbAuth: FirebaseAuth

    internal var currentLat = "0.0"
    internal var currentLong = "0.0"
    private val c_lat = "0.0"
    private val c_long = "0.0"

    private var timer: Timer? = null
    internal var UnreadNotificationCount = "0"

    private var gpsTracker: GPSTracker? = null
    internal var hashMap: HashMap<String, String>? = null
    var count: Int = 0

    override fun onBind(intent: Intent?): Binder? = null

    override fun onCreate() {
        super.onCreate()
        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference(fbAuth.currentUser?.uid)
        fbAuth = FirebaseAuth.getInstance()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this@UserlocationUpdateService, "Service Start",Toast.LENGTH_LONG).show()
        gpsTracker = GPSTracker(this@UserlocationUpdateService)

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

           // mMessageReference?.child(""+(count++))?.push( UserServerData(currentLat.toDouble(),currentLong.toDouble()))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {

        super.onDestroy()

        //Toast.makeText(this@UserlocationUpdateService, "Service Stop",Toast.LENGTH_LONG).show()
        println("-------------Service Stop-----------")
        try {
            timer!!.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}