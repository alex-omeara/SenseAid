package com.app.senseaid.data.repository

import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response.Failure
import com.app.senseaid.domain.model.Response.Success
import com.app.senseaid.domain.repository.AddLocationResponse
import com.app.senseaid.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FirestoreRepository {

    override val locations: Flow<List<Location>>
        get() =
            firestore.collection(LOCATION_COLLECTION).snapshots().map { snapshot -> snapshot.toObjects() }

    override suspend fun getLocation(uid: String): Location? =
        firestore.collection(LOCATION_COLLECTION).document(uid).get().await().toObject(Location::class.java)

    override suspend fun addLocation(title: String, img: String, imgDescription: String): AddLocationResponse {
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