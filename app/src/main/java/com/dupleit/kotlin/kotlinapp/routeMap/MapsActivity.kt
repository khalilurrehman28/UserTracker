package com.dupleit.kotlin.kotlinapp.routeMap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dupleit.kotlin.kotlinapp.R
import com.dupleit.kotlin.kotlinapp.UserServerData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.CameraPosition





class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    lateinit var mFirebaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var fbAuth: FirebaseAuth
    var TAG: String = "mapActivity"
    lateinit var locationList: ArrayList<LatLng>
    lateinit var line: Polyline


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        locationList = ArrayList()
        fbAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        mFirebaseReference = database.reference
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(30.3401778,76.8643139))      // Sets the center of the map to Mountain View
                .zoom(17f)                   // Sets the zoom
               // .bearing(90f)                // Sets the orientation of the camera to east
               // .tilt(30f)                   // Sets the tilt of the camera to 30 degrees
                .build()                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        line = mMap.addPolyline(PolylineOptions().width(20f).color(Color.RED).geodesic(true))
        getRoutes()
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(30.3610, 76.8485)))

    }

    private fun getRoutes() {
        val userDetails = mFirebaseReference.child(fbAuth.currentUser?.uid).child("journey").child(intent.getStringExtra("mappoints")).child("route")
        // Add a marker in Sydney and move the camera
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildName: String?) {
                Log.e(TAG, "onChildChanged:" + dataSnapshot)

                val userData = dataSnapshot?.getValue(UserServerData::class.java)!!
                locationList.add(LatLng(userData.userLat,userData.userLat))

                Log.e(TAG, "onChildAdded:" + userData.userLat+"--,--"+userData.userLang)
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
        drawRouteOnMap()
    }

    private fun drawRouteOnMap() {
        //locationList.forEach { println("userPoints-->"+it) }
        println("userPoints"+locationList.size)

        line.points = locationList
    }
}
