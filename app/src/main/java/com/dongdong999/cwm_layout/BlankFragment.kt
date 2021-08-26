package com.dongdong999.cwm_layout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BlankFragment : Fragment(), OnMapReadyCallback {


    private lateinit var googlemap : MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        googlemap = rootView.findViewById(R.id.fragment1_googleMap)
        googlemap.onCreate(savedInstanceState)
        googlemap.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val marker = LatLng(37.568291,126.997780)
        googleMap.addMarker(MarkerOptions().position(marker).title("여기"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }
    override fun onStart() {
        super.onStart()
        googlemap.onStart()
    }
    override fun onStop() {
        super.onStop()
        googlemap.onStop()
    }
    override fun onResume() {
        super.onResume()
        googlemap.onResume()
    }
    override fun onPause() {
        super.onPause()
        googlemap.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        googlemap.onLowMemory()
    }
    override fun onDestroy() {
        googlemap.onDestroy()
        super.onDestroy()
    }

}

