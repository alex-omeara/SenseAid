package com.app.senseaid.model.repository.impl

import android.util.Log
import com.app.senseaid.model.CategoryTags
import com.app.senseaid.model.Location
import com.app.senseaid.model.SensoryTags
import com.app.senseaid.model.Review
import com.app.senseaid.model.repository.FirestoreRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
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
            firestore.collection(LOCATIONS_COLLECTION).orderBy("avgRating", Direction.DESCENDING)
                .snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getLocation(locationId: String): Location? =
        firestore.collection(LOCATIONS_COLLECTION).document(locationId).get().await()
            .toObject<Location>()

    override fun getLocationCategory(
        tag: CategoryTags,
        filters: List<SensoryTags>
    ): Flow<List<Location>> {
        if (filters.isNotEmpty()) {
            Log.i("getLocationCategory", filters[0].name)
        } else {
            Log.i("getLocationCategory", "empty")
        }
        return firestore.collection(LOCATIONS_COLLECTION).orderBy("avgRating", Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                var categorisedList = snapshot.toObjects<Location>().filter { it.category == tag }
                if (filters.isNotEmpty()) {
                    categorisedList = categorisedList.filter {
                        filters.contains(it.top_tags[0]) || filters.contains(it.top_tags[1])
                    }
                }
                Log.i(
                    "getLocationCategory",
                    "categorisedList size:" + categorisedList.size.toString()
                )
                categorisedList
            }
    }

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
                imgDesc = imgDescription,
                coordinates = GeoPoint(0.0,0.0)
            )
            FirebaseFirestore.getInstance().collection("locations").document(locationId)
                .set(location).await()
        } catch (_: Exception) {
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
    ): String {
        val documentReference = firestore.collection(LOCATIONS_COLLECTION)
            .document(locationId)
            .collection(REVIEWS_COLLECTIONS).document()
        documentReference.set(
            mapOf(
                "author" to author,
                "rating" to rating,
                "tags" to tags,
                "content" to content,
                "sound_recording" to "/audio/${documentReference.id}-sound.mp3",
                "timestamp" to FieldValue.serverTimestamp()
            )
        )
        Log.d(TAG, "review added in locationId: $locationId, added review: ${documentReference.id}")
        return documentReference.id
    }


    companion object {
        private const val LOCATIONS_COLLECTION = "locations"
        private const val REVIEWS_COLLECTIONS = "reviews"
        private const val TAG = "FirestoreRepository"
    }
}