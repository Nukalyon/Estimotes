package com.estimote.proximity.fragments

import android.Manifest
import android.app.Fragment
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estimote.proximity.BuildConfig
import com.estimote.proximity.R
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


/**
 * A simple [Fragment] subclass.
 * Use the [Frag_Place_Publique.newInstance] factory method to
 * create an instance of this fragment.
 */
class Frag_Place_Publique : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request Location permission
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED) {
            println("Location Permission GRANTED")
        } else {
            println("Location Permission DENIED")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map = view!!.findViewById(R.id.map) as MapView
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        val zoomOnLocation = true
        if(!zoomOnLocation){
            val boundingBox = BoundingBox(48.4354,-70.9910,48.3850,-71.1227)
            map.zoomToBoundingBox(boundingBox,false)
            map.controller.setZoom(15.0)
        }else{
            // Get la position de lutilisateur
            val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
            myLocationOverlay.enableMyLocation()
            myLocationOverlay.enableFollowLocation()
            myLocationOverlay.isDrawAccuracyEnabled = true
            // Zoom vers la pos
            map.controller.animateTo(myLocationOverlay.myLocation)
            map.controller.setZoom(10.0)
            map.overlays.add(myLocationOverlay)

            // Scale Bar
            val dm : DisplayMetrics = context.resources.displayMetrics
            val scaleBarOverlay = ScaleBarOverlay(map)
            scaleBarOverlay.setCentred(true)
            scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
            map.overlays.add(scaleBarOverlay)

            /* Minimap Overlay
            val minimapOverlay = MinimapOverlay(context, map.tileRequestCompleteHandler);
            minimapOverlay.setWidth(dm.widthPixels / 5);
            minimapOverlay.setHeight(dm.heightPixels / 5);
            map.overlays.add(minimapOverlay);
            */
        }




    }
    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_publique, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() : Fragment =
            Frag_Place_Publique().apply {
            }
    }
}