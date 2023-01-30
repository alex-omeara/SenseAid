package com.app.senseaid.domain.repository

import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response
import com.app.senseaid.domain.model.Review
import kotlinx.coroutines.flow.Flow

typealias Locations = List<Location>
typealias LocationsResponse = Response<Locations>
typealias AddLocationResponse = Response<Boolean>

interface FirestoreRepository {
    val locations: Flow<List<Location>>

    suspend fun getReviews(uid: String): Flow<List<Review>>

//    suspend fun getReview(uid: String): Review?

    suspend fun getLocation(uid: String): Location?

    suspend fun addLocation(title: String, img: String, imgDescription: String): AddLocationResponse

    // TODO: Add deleteLocationFromFirestore method using firebase docs to do correctly
}