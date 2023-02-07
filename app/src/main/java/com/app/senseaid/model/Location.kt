package com.app.senseaid.model

import com.google.firebase.firestore.DocumentId

data class Location(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val imgPath: String = "",
    val imgDesc: String = "",
    val avgRating: Double? = null,
    val top_tags: List<String> = emptyList()
    ) {


    companion object {
        private const val TAG = "Location"
    }
}