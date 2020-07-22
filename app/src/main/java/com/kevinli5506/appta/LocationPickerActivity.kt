package com.kevinli5506.appta

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_location_picker.*
import java.io.IOException
import java.util.*

class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var mMap: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var fullAddressName: String = ""
    private lateinit var adapter: ArrayAdapter<String>
    private var warehouseLatLng: LatLng = LatLng(3.58789686, 98.69059861)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_picker)
        setSupportActionBar(map_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        map_sv_location_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val loc = map_sv_location_search.query.toString()
                var addressList: List<Address> = arrayListOf()
                if (loc != null || loc == "") {
                    val geocoder = Geocoder(this@LocationPickerActivity)
                    try {
                        addressList = geocoder.getFromLocationName(loc, 1)
                    } catch (e: IOException) {
                        val toast =
                            Toast.makeText(
                                this@LocationPickerActivity,
                                e.message,
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                    }
                    if (addressList.size > 0) {
                        val address: Address = addressList[0]
                        val latLng: LatLng = LatLng(address.latitude, address.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    } else {
                        Toast.makeText(
                            this@LocationPickerActivity,
                            "Alamat Tidak Ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        location_picker_btn_select_location.setOnClickListener {
            val results: FloatArray = FloatArray(1)
            Location.distanceBetween(
                warehouseLatLng.latitude,
                warehouseLatLng.longitude,
                latitude,
                longitude,
                results
            )
            val result = results[0]
            if (result > 30000) {
                Toast.makeText(
                    this,
                    "Maaf, untuk saat ini kami hanya menerima order dalam radius 30km dari warehouse (dalam lingkaran biru)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(map_sv_location_search.query.toString().equals("Indonesia")){
                Toast.makeText(
                    this,
                    "Harap memilih lokasi yang memiliki alamat",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val intent = Intent()
                intent.putExtra(EXTRA_LATITUDE, latitude)
                intent.putExtra(EXTRA_LONGITUDE, longitude)
                intent.putExtra(EXTRA_ADDRESS, fullAddressName)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
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

        // Add a marker in Sydney and move the camera

        enableMyLocation()

        val circle: CircleOptions = CircleOptions()
        circle.center(warehouseLatLng)
        circle.radius(30000.0)
        circle.strokeColor(Color.CYAN)
        circle.fillColor(0x55000077)
        circle.strokeWidth(2f)
        mMap.addCircle(circle)
        val markerOptions = MarkerOptions()
        markerOptions.position(warehouseLatLng)
        markerOptions.title("WareHouse")
        mMap.addMarker(markerOptions)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(warehouseLatLng, 15f))
        mMap.setOnCameraIdleListener {
            val geoCoder: Geocoder = Geocoder(this, Locale.getDefault())
            val curposition = mMap.cameraPosition.target
            val fullAddress =
                geoCoder.getFromLocation(curposition.latitude, curposition.longitude, 1)
            if (fullAddress.size > 0) {
                val address = fullAddress[0].getAddressLine(0)
                fullAddressName = address
                val addresstextarr = address.split(",")
                map_sv_location_search.setQuery(addresstextarr[0], false)
                map_sv_location_search.clearFocus()

            } else {
                map_sv_location_search.setQuery("Tidak ditemukan", false)
                map_sv_location_search.clearFocus()
            }
            longitude = curposition.longitude
            latitude = curposition.latitude
        }
        mMap.setOnCameraMoveListener {
            map_sv_location_search.setQuery("Loading", false)
            map_sv_location_search.clearFocus()
        }
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                finish()
            }
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }

    companion object {
        val REQUEST_LOCATION_PICKER_CODE = 1
        val EXTRA_LONGITUDE = "EXTRA_LONGITUDE"
        val EXTRA_LATITUDE = "EXTRA_LATITUDE"
        val EXTRA_ADDRESS = "EXTRA_ADDRESS"
    }

}
