package com.app.senseaid.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.model.Location
import com.app.senseaid.model.Review
import com.app.senseaid.model.SortDirection
import com.app.senseaid.model.Tags
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.firestore.Query.Direction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    val locations: MutableState<Flow<List<Location>>> =
        mutableStateOf(firestoreRepository.locations)

    var sortSelection by mutableStateOf(SortDirection.ASCENDING)
        private set

    val selectedTags = mutableStateMapOf<Tags, Boolean>()

    suspend fun filterLocations() {
        locations.value = firestoreRepository.getFilteredLocations(selectedTags.filterValues { true }.keys)
    }

    suspend fun sortLocations() {
        locations.value = when (sortSelection) {
            SortDirection.HIGHEST_RATING -> firestoreRepository.getSortedLocations(
                Direction.DESCENDING,
                true
            )
            SortDirection.LOWEST_RATING -> firestoreRepository.getSortedLocations(
                Direction.ASCENDING,
                true
            )
            SortDirection.DESCENDING -> firestoreRepository.getSortedLocations(
                Direction.DESCENDING,
                false
            )
            else -> firestoreRepository.getSortedLocations(Direction.ASCENDING, false)
        }
    }

    fun onTagSelect(tag: Tags) {
        selectedTags[tag] = selectedTags[tag] == false || selectedTags[tag] == null
        Log.d("tag state", "$tag : ${selectedTags[tag]}")
    }

    fun resetSort() {
        if (selectedTags.containsValue(true)) {
            onSortByPress(SortDirection.ASCENDING)
        }
    }

    fun onSortByPress(newSortDirection: SortDirection) {
        Log.i("sort selection", newSortDirection.toString())
        sortSelection = newSortDirection
    }

    fun onLocationPress(locationId: String, navToScreen: (String) -> Unit) {
        navToScreen("${LOCATION_SCREEN}/{${locationId}}")
    }

    // TODO: Delete or improve
    fun addData(context: Context) {
        var json: String? = null
        try {
            val inputStream = context.assets.open("mock-reviews.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.w("READ_JSON", e)
        }
        val listReview = object : TypeToken<List<Review>>() {}.type
        val t: List<Review> = Gson().fromJson(json, listReview)

        val c = t.chunked(14)
        Log.i("REVIEW", t.size.toString())

        Firebase.firestore.collection("locations").get().addOnSuccessListener { querySnapchot ->
            querySnapchot.documents.forEachIndexed { index, docSnapshot ->
                val collectionReference = docSnapshot.reference.collection("reviews")
                for (i in c[index]) {
                    collectionReference.document().set(i)
                }
            }
        }
    }
}
