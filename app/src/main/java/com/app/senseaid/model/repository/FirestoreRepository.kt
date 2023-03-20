package com.app.senseaid.model.repository

import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.Tags
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {
    val locations: Flow<List<Location>>

    suspend fun getSortedLocations(sortDirection: Query.Direction, sortByRating: Boolean): Flow<List<Location>>

    suspend fun getFilteredLocations(filters: Set<Tags>/*, sortDirection: Query.Direction, sortByRating: Boolean*/): Flow<List<Location>>

    suspend fun getLocation(locationId: String): Location?

    suspend fun updateLocationField(locationId: String, field: String, value: Any)

    suspend fun addLocation(title: String, img: String, imgDescription: String)

    suspend fun getReviews(locationId: String): Flow<List<Review>>

    suspend fun getReview(locationId: String, reviewUid: String): Review?

    suspend fun getSortedReviews(
        sortDirection: Query.Direction,
        locationId: String
    ): Flow<List<Review>>

    suspend fun addReview(
        author: String,
        rating: Double,
        tags: List<String>,
        content: String,
        locationId: String,
        soundRecording: String?
    )
    // TODO: Add deleteLocationFromFirestore method using firebase docs to do correctly

}