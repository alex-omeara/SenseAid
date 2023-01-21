package com.app.senseaid.data.repository

import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response.Failure
import com.app.senseaid.domain.model.Response.Success
import com.app.senseaid.domain.repository.AddLocationResponse
import com.app.senseaid.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FirestoreRepository {

    override suspend fun getLocationsFromFirestore() = callbackFlow {
        val snapshotListener = FirebaseFirestore.getInstance().collection(LOCATION_COLLECTION).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val locations = snapshot.toObjects(Location::class.java)
                Success(locations)
            } else {
                Failure(e)
            }
            trySend(response)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun addLocationToFirestore(title: String, img: String, imgDescription: String): AddLocationResponse {
        return try {
            val locationId = FirebaseFirestore.getInstance().collection(LOCATION_COLLECTION).document().id
            val location = Location(
                id = locationId,
                title = title,
                imgPath = img,
                imgDesc = imgDescription
            )
            FirebaseFirestore.getInstance().collection("locations").document(locationId).set(location).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    companion object {
        private const val LOCATION_COLLECTION = "locations"
    }
}