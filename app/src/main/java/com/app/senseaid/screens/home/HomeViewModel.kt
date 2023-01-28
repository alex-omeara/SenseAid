package com.app.senseaid.screens.home

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.app.senseaid.domain.model.Review
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    val locations = firestoreRepository.locations

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
                var collectionReference = docSnapshot.reference.collection("reviews")
                for (i in c[index]) {
                    collectionReference.document().set(i)
                }
            }
        }
    }
}
