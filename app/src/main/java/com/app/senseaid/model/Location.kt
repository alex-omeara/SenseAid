package com.app.senseaid.model

import com.google.firebase.firestore.DocumentId

data class Location(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val imgPath: String = "",
    val imgDesc: String = "",
    val avgRating: Double = 0.0,
    val top_tags: List<LocationTags> = emptyList(),
    val category: CategoryTags = CategoryTags.DEFAULT,
    val totalReviews: Int = 0
)