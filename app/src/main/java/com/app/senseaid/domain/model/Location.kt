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
    }
}