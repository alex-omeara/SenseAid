package com.app.senseaid.screens.location

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
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {
    val location = mutableStateOf(Location())
//    var reviews: Flow<List<Review>> = emptyFlow()

    val reviews: MutableState<Flow<List<Review>>> =
        mutableStateOf(emptyFlow())

    var isSortReviews by mutableStateOf(false)
        private set

    var sortSelection by mutableStateOf(SortDirection.ASCENDING)
        private set

    fun toggleSortReviews() {
        isSortReviews = !isSortReviews
    }

    fun onSortByPress(newSortDirection: SortDirection) {
        Log.i("sort selection", newSortDirection.toString())
        sortSelection = newSortDirection
    }

    fun initialise(locationId: String) {
        launchCatching {
            if (locationId != DEFAULT_ID) {
                val uid = locationId.substring(1, locationId.length - 1)
                location.value = firestoreRepository.getLocation(uid) ?: Location()
//                reviews = firestoreRepository.getReviews(uid)
                reviews.value = firestoreRepository.getReviews(uid)
            }
        }
    }

    suspend fun sortReviews() {
        reviews.value = when (sortSelection) {
            SortDirection.LOWEST_RATING -> firestoreRepository.getSortedReviews(
                Query.Direction.ASCENDING,
                location.value.id
            )
            else -> firestoreRepository.getSortedReviews(Query.Direction.DESCENDING, location.value.id)
        }
    }

    fun onReviewPress(locationId: String, reviewId: String, navToScreen: (String) -> Unit) {
        navToScreen("${REVIEW_SCREEN}/{${locationId}}/{${reviewId}}")
    }


    fun onAddReview(location: Location, navToScreen: (String) -> Unit) {
        navToScreen("${ADD_REVIEW_SCREEN}/{${location.id}}/{${location.totalReviews}}/{${location.avgRating}}")
    }
}