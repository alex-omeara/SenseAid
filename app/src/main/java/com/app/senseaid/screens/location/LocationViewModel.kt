package com.app.senseaid.screens.location

import androidx.compose.runtime.mutableStateOf
import com.app.senseaid.Routes.DEFAULT_ID
import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Review
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {
    val location = mutableStateOf(Location())
    var reviews: Flow<List<Review>> = emptyFlow()


    fun initialise(locationId: String) {
        launchCatching {
            if (locationId != DEFAULT_ID) {
                val uid = locationId.substring(1, locationId.length - 1)
                location.value = firestoreRepository.getLocation(uid) ?: Location()
                reviews = firestoreRepository.getReviews(uid)
            }
        }
    }
}