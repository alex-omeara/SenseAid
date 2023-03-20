package com.app.senseaid.model.repository.impl

import android.util.Log
import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.Tags
import com.app.senseaid.model.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import com.google.firebase.firestore.SetOptions
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
            firestore.collection(LOCATIONS_COLLECTION).orderBy("title")
                .snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getSortedLocations(
        sortDirection: Direction,
        sortByRating: Boolean
    ): Flow<List<Location>> {
        val field = if (sortByRating) "avgRating" else "title"
        return firestore.collection(LOCATIONS_COLLECTION).orderBy(field, sortDirection)
            .snapshots()
            .map { snapshot -> snapshot.toObjects() }
    }

    override suspend fun getFilteredLocations(filters: Set<Tags>): Flow<List<Location>> {
        return firestore.collection(LOCATIONS_COLLECTION)
            .whereArrayContainsAny("top_tags", filters.map { it.toString() })
            .snapshots()
            .map { snapshot -> snapshot.toObjects() } }

    override suspend fun getLocation(locationId: String): Location? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId).get().await()
            .toObject<Location>()

    override suspend fun updateLocationField(locationId: String, field: String, value: Any) {
        firestore.collection(LOCATIONS_COLLECTION).document(locationId).set(
            mapOf(
                field to value
            ), SetOptions.merge()
        )
    }

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


    override suspend fun getReviews(locationId: String): Flow<List<Review>> =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS).snapshots()
            .map { snapshot -> snapshot.toObjects() }

    override suspend fun getReview(locationId: String, reviewUid: String): Review? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS).document(reviewUid).get().await().toObject<Review>()

    override suspend fun getSortedReviews(
        sortDirection: Direction,
        locationId: String
    ): Flow<List<Review>> {
        return firestore.collection(LOCATIONS_COLLECTION).document(locationId)
            .collection(REVIEWS_COLLECTIONS)
            .orderBy("rating", sortDirection)
            .snapshots()
            .map { snapshot -> snapshot.toObjects() }
    }

    override suspend fun addReview(
        author: String,
        rating: Double,
        tags: List<String>,
        content: String,
        locationId: String,
        soundRecording: String?
    ) {
        val documentReference = firestore.collection(LOCATIONS_COLLECTION)
            .document(locationId)
            .collection(REVIEWS_COLLECTIONS).document()
        documentReference.set(
            mapOf(
                "author" to author,
                "rating" to rating,
                "tags" to tags,
                "content" to content,
                "sound_recording" to soundRecording
            )
        )
        Log.d(TAG, "review added in locationId: $locationId, added review: ${documentReference.id}")
    }


    companion object {
        private const val LOCATIONS_COLLECTION = "locations"
        private const val REVIEWS_COLLECTIONS = "reviews"
        private const val TAG = "FirestoreRepository"
    }
}