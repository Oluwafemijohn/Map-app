package com.decagon.android.sq007

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var databaseRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        databaseRef= FirebaseDatabase.getInstance().reference
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        databaseRef.addValueEventListener(logListener)



    }

    //Getting location permission
    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }

    //Show alert dialog
    fun showSettingAlert() {

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            alertDialog.setTitle("GPS setting!")
            alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ")
            alertDialog.setPositiveButton("Setting",
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    this.startActivity(intent)
                })
            alertDialog.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            alertDialog.show()
    }


    //permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager
                                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return
                }
                //Setting the location permission true
                map.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }


    //getting location
    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 4000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //Handling Callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    val userLatAndLong = MyLatLongModel(location.latitude, location.longitude)
                    databaseRef.child("FemiLocation").setValue(userLatAndLong).addOnSuccessListener {    }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext,"Error occurred while writing the locations", Toast.LENGTH_LONG).show()
                        }
                    if (location != null) {
                        //Clear the positon before inserting another
//                        map.clear()
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions1  = map.addMarker(MarkerOptions().position(latLng).title("Mr Femi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) )
                        markerOptions1?.position = latLng
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
                    }
                }
            }
        }
    }

    //Loading the map
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //get location access
        getLocationAccess()
        //get the location update
        getLocationUpdates()
        //
        startLocationUpdates()
        showSettingAlert()
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    val logListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG).show()
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {

                //Getting partner location
                val locationlogging = dataSnapshot.child("DennisLocation").getValue(PartnerLocationModel::class.java)
                var partnersLocation=locationlogging?.Latitude
                var partnerPositionModel=locationlogging?.Longitude
                if (partnersLocation !=null  && partnerPositionModel != null) {
                    map.clear()
                    val driverLoc = LatLng(partnersLocation, partnerPositionModel)
                    val markerOptions2 = map.addMarker(MarkerOptions().position(driverLoc).title("Mr Dennis")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                    markerOptions2?.position = driverLoc
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLoc, 20f))
                }
            }
        }
    }

}