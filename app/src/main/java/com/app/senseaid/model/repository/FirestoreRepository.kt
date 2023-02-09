package com.app.senseaid.model.repository

import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.Tags
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {
    val locations: Flow<List<Location>>

    suspend fun getReviews(locationId: String): Flow<List<Review>>

    suspend fun getReview(locationId: String, reviewUid: String): Review?

    suspend fun addReview(
        author: String,
        rating: Double,
        tags: List<String>,
        content: String,
        locationId: String
    )

    suspend fun updateLocationField(locationId: String, field: String, value: Any)

    suspend fun getLocation(locationId: String): Location?

    suspend fun addLocation(title: String, img: String, imgDescription: String)

    // TODO: Add deleteLocationFromFirestore method using firebase docs to do correctly
}