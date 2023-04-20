package com.app.senseaid.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.*
import com.app.senseaid.Routes.CATEGORY_SCREEN
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.model.*
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository,
) : SenseAidViewModel() {
    var curGeoLocation by mutableStateOf(GeoLocation(0.0, 0.0))
        private set
    var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null

    private var locations: MutableState<Flow<List<Location>>> =
        mutableStateOf(emptyFlow())

    val sensoryTags = mutableStateListOf<SensoryTags>()

    fun getLatLong(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        if (checkAndRequestPermission(
                context = context,
                permissions = permissions,
                launcher = launcher
            )) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    for (loc in p0.locations) {
                        curGeoLocation = GeoLocation(loc.latitude, loc.longitude)
                    }
                }
            }
            startLocationUpdates()
        }
    }
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 300)
                .build()
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    fun getLocationDistance(location: Location): Double {
        val geoLocation = GeoLocation(location.coordinates.latitude, location.coordinates.longitude)
        return GeoFireUtils.getDistanceBetween(geoLocation, curGeoLocation) / 1000
    }

    fun getCategoryLocations(
        tag: CategoryTags?,
        locationTag: SensoryTags?
    ): MutableState<Flow<List<Location>>> {
        locations = if (tag == null) {
            mutableStateOf(firestoreRepository.locations)
        } else {
            if (locationTag != null) {
                if (!sensoryTags.remove(locationTag)) {
                    sensoryTags.add(locationTag)
                }
            }
            mutableStateOf(firestoreRepository.getLocationCategory(tag, sensoryTags.toList()))
        }
        return locations
    }

    fun onCategoryClick(tag: CategoryTags, navToScreen: (String) -> Unit) {
        Log.i("onCategoryClick", "navigating")
        navToScreen("${CATEGORY_SCREEN}/{${tag.ordinal}}")
    }

    fun onLocationClick(locationId: String, navToScreen: (String) -> Unit) {
        Log.i("onLocationClick", "navigating")
        navToScreen("${LOCATION_SCREEN}/{${locationId}}")
    }
}
