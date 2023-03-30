package com.app.senseaid.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint

data class Location(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val imgPath: String = "",
    val imgDesc: String = "",
    val avgRating: Double = 0.0,
    val top_tags: List<SensoryTags> = emptyList(),
    val category: CategoryTags = CategoryTags.DEFAULT,
    val totalReviews: Int = 0,
    val coordinates: GeoPoint = GeoPoint(0.0,0.0)
)

