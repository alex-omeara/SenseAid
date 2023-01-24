package com.app.senseaid.screens.location

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.app.senseaid.Routes.LOCATION_DEFAULT_ID
import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : SenseAidViewModel() {
    val location = mutableStateOf(Location())

    fun initialise(locationId: String) {
        launchCatching {
            if (locationId != LOCATION_DEFAULT_ID) {
                location.value = repository.getLocation(locationId.substring(1, locationId.length - 1)) ?: Location()
            }
        }
    }
}