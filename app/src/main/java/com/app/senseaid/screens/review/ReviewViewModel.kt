package com.app.senseaid.screens.review

import androidx.compose.runtime.mutableStateOf
import com.app.senseaid.Routes
import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Review
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {
    val review = mutableStateOf(Review())

//    fun initialise(locationId: String) {
//        launchCatching {
//            if (locationId != Routes.DEFAULT_ID) {
//                val uid = locationId.substring(1, locationId.length - 1)
//                review.value = firestoreRepository.getLocation(uid) ?: Location()
//            }
//        }
//    }
}