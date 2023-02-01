package com.app.senseaid.domain.model

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

data class Review(
    @DocumentId
    val id: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("rating")
    val rating: Double = 0.0,
    @SerializedName("tags")
    val tags: String = "",
    @SerializedName("content")
    val content: String = ""
)
