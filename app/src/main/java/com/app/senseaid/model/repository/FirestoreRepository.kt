package com.app.senseaid.model.repository

import com.app.senseaid.model.*
import com.google.firebase.firestore.Query.Direction
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {
    val locations: Flow<List<Location>>

    suspend fun getLocation(locationId: String): Location?

    fun getLocationCategory(tag: CategoryTags, filters: List<SensoryTags>): Flow<List<Location>>

    suspend fun updateLocationField(locationId: String, field: String, value: Any)

    suspend fun addLocation(title: String, img: String, imgDescription: String)

    suspend fun getReviews(locationId: String): Flow<List<Review>>

    suspend fun getReview(locationId: String, reviewUid: String): Review?

    suspend fun getSortedReviews(
        sortDirection: Direction,
        locationId: String
    ): Flow<List<Review>>

    suspend fun addReview(
        author: String,
        rating: Double,
        tags: List<String>,
        content: String,
        locationId: String,
    ): String
}