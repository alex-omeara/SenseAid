package com.app.senseaid.data.repository

import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response.Failure
import com.app.senseaid.domain.model.Response.Success
import com.app.senseaid.domain.model.Review
import com.app.senseaid.domain.repository.AddLocationResponse
import com.app.senseaid.domain.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {

    override val locations: Flow<List<Location>>
        get() =
            firestore.collection(LOCATIONS_COLLECTION).snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getReviews(uid: String): Flow<List<Review>> =
        firestore.collection(LOCATIONS_COLLECTION).document(uid)
            .collection(REVIEWS_COLLECTIONS).snapshots()
            .map { snapshot ->
                snapshot.toObjects()
            }

    override suspend fun getReview(locationUid: String, reviewUid: String): Review? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationUid)
            .collection(REVIEWS_COLLECTIONS).document(reviewUid).get().await().toObject<Review>()

    override suspend fun getLocation(uid: String): Location? =
        firestore.collection(LOCATIONS_COLLECTION).document(uid).get().await()
            .toObject<Location>()

    override suspend fun addLocation(
        title: String,
        img: String,
        imgDescription: String
    ): AddLocationResponse {
        return try {
            val locationId =
                FirebaseFirestore.getInstance().collection(LOCATIONS_COLLECTION).document().id
            val location = Location(
                id = locationId,
                title = title,
                imgPath = img,
                imgDesc = imgDescription
            )
            FirebaseFirestore.getInstance().collection("locations").document(locationId)
                .set(location).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


    companion object {
        private const val LOCATIONS_COLLECTION = "locations"
        private const val REVIEWS_COLLECTIONS = "reviews"
    }
}