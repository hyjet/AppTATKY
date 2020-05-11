package com.kevinli5506.appta

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.core.exceptions.ServicesException

import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style

import com.mapbox.mapboxsdk.style.layers.Property.NONE
import com.mapbox.mapboxsdk.style.layers.Property.VISIBLE
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.activity_loacation_picker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {
    val DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private lateinit var mapboxMap: MapboxMap
    private var permissionsManager: PermissionsManager = PermissionsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_loacation_picker)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)


    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.MAPBOX_STREETS) { style ->
            enableLocationComponent(style)
            val hoveringMarker = ImageView(this)
            hoveringMarker.setImageResource(R.drawable.mapbox_marker_icon_default)
            val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            hoveringMarker.layoutParams = params
            mapView.addView(hoveringMarker)
            initDroppedMarker(style)
            mapboxMap.addOnMoveListener(object :MapboxMap.OnMoveListener{
                override fun onMoveBegin(detector: MoveGestureDetector) {

                }

                override fun onMove(detector: MoveGestureDetector) {
                    hoveringMarker.visibility = View.VISIBLE
                    val droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
                    droppedMarkerLayer?.setProperties(PropertyFactory.visibility(NONE))
                }

                override fun onMoveEnd(detector: MoveGestureDetector) {
                    val mapTargetLatLng: LatLng = mapboxMap.cameraPosition.target
                    hoveringMarker.visibility = View.INVISIBLE
                    if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                        var source: GeoJsonSource? = style.getSourceAs("dropped-marker-source-id")
                        source?.setGeoJson(
                            Point.fromLngLat(
                                mapTargetLatLng.longitude,
                                mapTargetLatLng.latitude
                            )
                        )
                        var droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID)
                        droppedMarkerLayer?.setProperties(PropertyFactory.visibility(VISIBLE))
                    }
                    reverseGeocode(
                        Point.fromLngLat(
                            mapTargetLatLng.longitude,
                            mapTargetLatLng.latitude
                        )
                    )
                }

            })

        }

    }

    private fun reverseGeocode(point: Point) {
        try {
            val client: MapboxGeocoding = MapboxGeocoding.builder()
                .accessToken(getString(R.string.mapbox_access_token))
                .query(Point.fromLngLat(point.longitude(), point.latitude()))
                .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                .build()
            client.enqueueCall(object : Callback<GeocodingResponse> {
                override fun onResponse(
                    call: Call<GeocodingResponse>,
                    response: Response<GeocodingResponse>
                ) {
                    var results = response.body()?.features()
                    if (results!!.size > 0) {
                        val feature = results[0]
                        mapboxMap.getStyle() { style ->
                            if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                                select_location_name.text = feature.placeName().toString()
                            }
                        }
                    } else {
                        select_location_name.text = getString(R.string.not_found)
                    }
                }

                override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                    t.printStackTrace()
                }


            })
        } catch (servicesException: ServicesException) {
            servicesException.printStackTrace()
        }

    }


    private fun initDroppedMarker(loadedMapStyle: Style) {
        loadedMapStyle.addImage(
            "dropped-icon-image",
            resources.getDrawable(R.drawable.purple_marker, this.theme)
        )
        loadedMapStyle.addSource(GeoJsonSource("dropped-marker-source-id"))
        loadedMapStyle.addLayer(
            SymbolLayer(DROPPED_MARKER_LAYER_ID, "dropped-marker-source-id").withProperties(
                PropertyFactory.iconImage("dropped-icon-image"),
                PropertyFactory.visibility(NONE),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true)
            )
        )
    }

    private fun enableLocationComponent(loadedMapStyle: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Create and customize the LocationComponent's options
            val customLocationComponentOptions = LocationComponentOptions.builder(this)
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(this, R.color.backgroundPrimary))
                .build()

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, loadedMapStyle)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()

// Get an instance of the LocationComponent and then adjust its settings
            mapboxMap.locationComponent.apply {

// Activate the LocationComponent with options
                activateLocationComponent(locationComponentActivationOptions)

// Enable to make the LocationComponent visible
                isLocationComponentEnabled = true

// Set the LocationComponent's camera mode
                cameraMode = CameraMode.TRACKING

// Set the LocationComponent's render mode
                renderMode = RenderMode.COMPASS
            }
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(this, "Need Permission", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent(mapboxMap.style!!)
        } else {
            Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}


