package com.app.senseaid.screens.review

import androidx.compose.runtime.mutableStateOf
import com.app.senseaid.Routes.DEFAULT_ID
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

    fun initialise(locationUid: String, reviewUid: String) {
        launchCatching {
            if (locationUid != DEFAULT_ID && reviewUid != DEFAULT_ID) {
                review.value = firestoreRepository.getReview(
                    locationUid.substring(1, locationUid.length - 1),
                    reviewUid.substring(1, reviewUid.length - 1)
                ) ?: Review()
            }
        }
    }
}