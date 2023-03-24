package com.app.senseaid.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import com.app.senseaid.Routes.CATEGORY_SCREEN
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.model.*
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    var locations: MutableState<Flow<List<Location>>> =
        mutableStateOf(emptyFlow())

    val locationTags = mutableStateListOf<LocationTags>()

    var isSearching by mutableStateOf(false)
        private set

    var searchText by mutableStateOf("")
        private set

    fun getCategoryLocations(
        tag: CategoryTags?,
        locationTag: LocationTags?
    ): MutableState<Flow<List<Location>>> {
        locations = if (tag == null) {
            Log.i("getCategoryLocations", "null")
            mutableStateOf(firestoreRepository.locations)
        } else {
            Log.i("getCategoryLocations", "not null")
            if (locationTag != null) {
                if (!locationTags.remove(locationTag)) {
                    locationTags.add(locationTag)
                }
            }
            mutableStateOf(firestoreRepository.getLocationCategory(tag, locationTags.toList()))
        }
        return locations
    }

    fun onCategoryClick(tag: CategoryTags, navToScreen: (String) -> Unit) {
        Log.i("onCategoryClick", "navigating")
        navToScreen("${CATEGORY_SCREEN}/{${tag.ordinal}}")
    }

    fun onLocationClick(locationId: String, navToScreen: (String) -> Unit) {
        navToScreen("${LOCATION_SCREEN}/{${locationId}}")
    }

    fun updateIsSearching() {
        isSearching = !isSearching
    }

    fun updateSearchText(input: String) {
        searchText = input
    }
    fun onSearchClick(queryText: String, tag: CategoryTags?): MutableState<Flow<List<Location>>> {
        locations = mutableStateOf(firestoreRepository.searchLocations(queryText))
        return locations
//        launchCatching {
//            locations = if (tag == null) {
//                mutableStateOf(firestoreRepository.searchLocations(queryText))
//            } else {
//                mutableStateOf(firestoreRepository.searchLocations(queryText, tag, locationTags))
//            }
//        }

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
