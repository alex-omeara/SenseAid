package com.app.senseaid.screens.location

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.app.senseaid.Routes.ADD_REVIEW_SCREEN
import com.app.senseaid.Routes.DEFAULT_ID
import com.app.senseaid.Routes.REVIEW_SCREEN
import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.SortDirection
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.firestore.Query.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {
    val location = mutableStateOf(Location())

    val reviews: MutableState<Flow<List<Review>>> =
        mutableStateOf(emptyFlow())

    var isSorting by mutableStateOf(false)
        private set

    var sortSelection by mutableStateOf(SortDirection.ASCENDING)
        private set

    fun toggleSortReviews() {
        isSorting = !isSorting
    }

    fun initialise(locationId: String) {
        launchCatching {
            if (locationId != DEFAULT_ID) {
                val uid = locationId.substring(1, locationId.length - 1)
                location.value = firestoreRepository.getLocation(uid) ?: Location()
                reviews.value = firestoreRepository.getReviews(uid)
            }
        }
    }

    suspend fun sortReviews(newSortDirection: SortDirection) {
        sortSelection = newSortDirection
        reviews.value = if (sortSelection == SortDirection.HIGHEST_RATING) {
            firestoreRepository.getSortedReviews(Direction.DESCENDING, location.value.id)
        } else {
            firestoreRepository.getSortedReviews(Direction.ASCENDING, location.value.id)
        }
    }

    fun onReviewPress(locationId: String, reviewId: String, navToScreen: (String) -> Unit) {
        navToScreen("${REVIEW_SCREEN}/{${locationId}}/{${reviewId}}")
    }

    fun onAddReview(location: Location, navToScreen: (String) -> Unit) {
        navToScreen("${ADD_REVIEW_SCREEN}/{${location.id}}/{${location.totalReviews}}/{${location.avgRating}}")
    }
}