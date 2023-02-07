package com.app.senseaid.screens.review

import androidx.compose.runtime.mutableStateOf
import com.app.senseaid.Routes.DEFAULT_ID
import com.app.senseaid.model.Review
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {
    val review = mutableStateOf(Review())

    fun initialise(locationId: String, reviewUid: String) {
        launchCatching {
            if (locationId != DEFAULT_ID && reviewUid != DEFAULT_ID) {
                review.value = firestoreRepository.getReview(
                    locationId.substring(1, locationId.length - 1),
                    reviewUid.substring(1, reviewUid.length - 1)
                ) ?: Review()
            }
        }
    }
}