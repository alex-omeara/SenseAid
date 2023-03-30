package com.app.senseaid.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Review(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val rating: Double = 0.0,
    val tags: List<String> = emptyList(),
    val content: String = "",
    val sound_recording: String? = null,
    val timestamp: Timestamp? = null
)
