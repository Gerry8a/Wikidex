package com.gochoa.wikidex.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gochoa.wikidex.R
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.data.service.DefaultLocationClient
import com.gochoa.wikidex.data.service.LocationClient
import com.gochoa.wikidex.databinding.FragmentLocationBinding
import com.gochoa.wikidex.domain.model.Marker
import com.gochoa.wikidex.presentation.location.adapter.LocationAdapter
import com.gochoa.wikidex.utils.DateFormatted.getDate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var binding: FragmentLocationBinding
    private var locationPermissionAccepted = false
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var locationClient: LocationClient
    private lateinit var locationAdapter: LocationAdapter
    private var listMarkes = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationClient = DefaultLocationClient(
            requireContext(),
            LocationServices.getFusedLocationProviderClient(requireContext())
        )
        addMarker()
        getMarkers()
        buildObservers()
    }

    private fun addMarker() {
        if (locationPermissionAccepted) {

        }
        lifecycleScope.launch {
            val result = locationClient.getLocationUpdates()
            result.collect { location ->
                val makerModel = Marker().apply {
                    latitude = location.latitude
                    longitude = location.longitude
                    date = getDate(requireContext())
                }
                viewModel.addMarker(makerModel)
            }
        }
    }

    private fun getMarkers() {
        val db = FirebaseFirestore.getInstance()
        lifecycleScope.launch {
            db.collection("markers")
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    for (doc in value!!) {
                        for (document in value) {
                            val marker = document.toObject(Marker::class.java)
                            listMarkes.add(marker)
                            printMarkers(marker)
                        }
//                        printMarkers(markers)
//                        fillList(markers)
                    }
                }
        }
    }

    private fun fillList() {
        locationAdapter = LocationAdapter(listMarkes)
        binding.rvCordinates.apply {
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun printMarkers(marcadores: Marker) {
        val sydney = LatLng(marcadores.latitude, marcadores.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title(marcadores.date)
        )
        fillList()
    }

    private fun buildObservers() {
        viewModel.status.observe(requireActivity()) {
            when (it) {
                is ApiResponseStatus.Error -> {}
                is ApiResponseStatus.Loading -> {}
                is ApiResponseStatus.Success -> {
                    for (i in it.data.result) {
                        val grg = i.toObject(Marker::class.java)
                        Log.d("ggg", "${grg.longitude}")
                    }
                    Log.d("ggg", "{${it.data.result.toString()}}")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            locationPermissionAccepted = isGranted

        }

    fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                locationPermissionAccepted = true
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                /**
                 * Descripción de porqué debe aceptar el permiso
                 */
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
//                Toast.makeText(
//                    requireContext(),
//                    getString(R.string.permission_needed), Toast.LENGTH_SHORT
//                ).show()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }


}