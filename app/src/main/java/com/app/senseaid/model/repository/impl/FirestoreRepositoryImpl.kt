package com.app.senseaid.model.repository.impl

import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.Tags
import com.app.senseaid.model.repository.FirestoreRepository
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

    override suspend fun getReviews(locationId: String): Flow<List<Review>> =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS).snapshots()
            .map { snapshot -> snapshot.toObjects() }

    override suspend fun getReview(locationId: String, reviewUid: String): Review? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS).document(reviewUid).get().await().toObject<Review>()

    override suspend fun addReview(review: Review, locationId: String) {
        firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS).document().set(review)
    }

    override suspend fun getLocation(locationId: String): Location? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId).get().await()
            .toObject<Location>()

    override suspend fun addLocation(
        title: String,
        img: String,
        imgDescription: String
    ) {
        try {
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
        } catch (e: Exception) {
        }
    }


    companion object {
        private const val LOCATIONS_COLLECTION = "locations"
        private const val REVIEWS_COLLECTIONS = "reviews"
    }
}