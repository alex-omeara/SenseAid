package com.app.senseaid.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

data class Location(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val imgPath: String = "",
    val imgDesc: String = "",
    val avgRating: Double? = null
    ) {


    companion object {
        private const val TAG = "Location"
    }
}