package com.dupleit.kotlin.kotlinapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import com.dupleit.kotlin.kotlinapp.BackgroundService.UserlocationUpdateService
import com.dupleit.kotlin.kotlinapp.userHistoryList.userHistoryList
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.ActionBar
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.main_action.view.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val abar = supportActionBar
        abar!!.setBackgroundDrawable(resources.getDrawable(R.drawable.main_background))//line under the action bar
        val viewActionBar = layoutInflater.inflate(R.layout.main_action, null)
        val params = ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)
        val customFont1 = Typeface.createFromAsset(assets, "fonts/LatoRegular.ttf")
        val textviewTitle = viewActionBar.actionbar_textview
        textviewTitle.typeface = customFont1
        textviewTitle.text = this.getString(R.string.app_name)
        abar.setCustomView(viewActionBar, params)
        abar.setDisplayShowCustomEnabled(true)
        abar.setDisplayShowTitleEnabled(false)
        //abar.setDisplayHomeAsUpEnabled(true)
        //abar.setIcon(R.color.transparent)
        //abar.setHomeButtonEnabled(true)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Toast.makeText(applicationContext, intent.getStringExtra("mappoints"), Toast.LENGTH_LONG).show()

        startTrack.visibility = View.VISIBLE
        seeHistory.visibility = View.VISIBLE
        StopTrack.visibility = View.GONE

        startTrack.setOnClickListener{ checkPermissionRun() }
        StopTrack.setOnClickListener { stopService() }
        seeHistory.setOnClickListener { gotohistoryActivity() }
    }

    private fun gotohistoryActivity() {
        val intent = Intent(applicationContext, userHistoryList::class.java)
        if (applicationContext != null) { startActivity(intent) }
    }

    private fun checkPermissionRun() {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setupPermissions()
        } else { runService() }
    }

    private fun runService() {

        val intent = Intent(applicationContext, UserlocationUpdateService::class.java)
        if (applicationContext != null) {
            applicationContext.startService(intent)
        }
        startTrack.visibility = View.GONE
        seeHistory.visibility = View.GONE
        StopTrack.visibility =View.VISIBLE
    }

    private fun stopService() {

        val intent = Intent(applicationContext, UserlocationUpdateService::class.java)
        if (applicationContext != null) {
            applicationContext.stopService(intent)
        }
        StopTrack.visibility =View.GONE
        startTrack.visibility = View.VISIBLE
        seeHistory.visibility = View.VISIBLE
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),RECORD_REQUEST_CODE)
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
