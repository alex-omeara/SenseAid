package com.app.senseaid.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.model.Review
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    val locations = firestoreRepository.locations

    var filterState by mutableStateOf(false)
        private set

    fun onLocationPress(locationId: String, navToScreen: (String) -> Unit) {
        navToScreen("${LOCATION_SCREEN}/{${locationId}}")
    }

    fun onFilterPress() {
        filterState = !filterState
        Log.i("filter press", "${!filterState} => $filterState")
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
