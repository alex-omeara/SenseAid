package com.app.senseaid.domain.repository

import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Locations = List<Location>
typealias LocationsResponse = Response<Locations>
typealias AddLocationResponse = Response<Boolean>

interface FirestoreRepository {
    val locations: Flow<List<Location>>

    suspend fun addLocationToFirestore(title: String, img: String, imgDescription: String): AddLocationResponse

    // TODO: Add deleteLocationFromFirestore method using firebase docs to do correctly
}