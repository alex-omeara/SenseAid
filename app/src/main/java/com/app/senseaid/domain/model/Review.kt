package com.app.senseaid.domain.model

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

data class Review(
    @DocumentId
    var id: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("author")
    var author: String = "",
    @SerializedName("rating")
    var rating: Double = 0.0,
    @SerializedName("tags")
    var tags: String = "",
    @SerializedName("content")
    var content: String = ""
)
