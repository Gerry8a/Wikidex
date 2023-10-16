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
import com.gochoa.wikidex.R
import com.gochoa.wikidex.data.remote.ApiResponseStatus
import com.gochoa.wikidex.databinding.FragmentLocationBinding
import com.gochoa.wikidex.domain.model.Marker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var binding: FragmentLocationBinding
    private var locationPermissionAccepted = false
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

        val marker = hashMapOf(
            "latitude" to 999.0, "longitude" to 999.0
        )
        viewModel.addMarker(marker)
//        ddfdf(marker)

//        val db = FirebaseFirestore.getInstance()
//
//        db.collection("markers")
//            .get()
//            .addOnSuccessListener { result ->
//                Log.d("ggg", "getMarkers:")
//                for (documen in result) {
//                    val grg = documen.toObject(Marker::class.java)
//                    Log.d("ggg", "${grg.longitude} ggg")
////                    ggg = Mapper().fromSnapShotToModel(result) as MutableList<Marker>
//                }
//                result
//            }
//            .addOnFailureListener {
//                Log.d("ggg", "getMarkers: ${it.message.toString()}")
//                it.message
//            }

        viewModel.status.observe(requireActivity()) {
            when (it) {
                is ApiResponseStatus.Error -> {}
                is ApiResponseStatus.Loading -> {}
                is ApiResponseStatus.Success -> {
                    for (i in it.data.result){
                        val grg = i.toObject(Marker::class.java)
                    Log.d("ggg", "${grg.longitude}")
                    }
                    Log.d("ggg", "{${it.data.result.toString()}}")
                }
            }
        }

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