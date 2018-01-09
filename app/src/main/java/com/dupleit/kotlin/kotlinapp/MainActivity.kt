package com.dupleit.kotlin.kotlinapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.dupleit.kotlin.kotlinapp.BackgroundService.UserlocationUpdateService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startTrack.setOnClickListener({v->
            checkPermissionRun()
        })

        StopTrack.setOnClickListener { v->stopService() }
    }

    private fun checkPermissionRun() {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setupPermissions()
        } else {
            runService()
        }
    }

    private fun runService() {
        val intent = Intent(applicationContext, UserlocationUpdateService::class.java)
        if (applicationContext != null) {
            applicationContext.startService(intent)
        }
    }

    private fun stopService() {
        val intent = Intent(applicationContext, UserlocationUpdateService::class.java)
        if (applicationContext != null) {
            applicationContext.stopService(intent)
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Permission to access the Location is required for this app to record Activity.").setTitle("Permission required")
                builder.setPositiveButton("OK"
                ) { dialog, id ->
                    Log.i(TAG, "Clicked")
                    makeRequest()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                makeRequest()
            }
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                    runService()
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}
