package com.app.senseaid.domain.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: String = "",
    val title: String = "",
    val imgPath: String = "",
    val imgDesc: String = ""
    ) : Parcelable {

    companion object {
        private const val TAG = "Location"

//        fun DocumentSnapshot.toLocation(): Location? {
//            try {
////                val averageRating = getDouble("avgRating")!!
//                val imgDescription = getString("imgDesc")!!
//                val imgPath = getString("imgPath")!!
//                val title = getString("title")!!
//                return Location(title, imgPath, imgDescription)
//            } catch (e: Exception) {
//                Log.e(TAG, "Error converting location", e)
//                // TODO: Add FirebaseCrashlytics as seen https://medium.com/firebase-developers/android-mvvm-firestore-37c3a8d65404
//                return null
//            }
//        }
    }
}