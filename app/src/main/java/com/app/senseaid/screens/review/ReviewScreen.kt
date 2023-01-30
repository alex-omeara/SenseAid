package com.app.senseaid.screens.review

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReviewScreen(
    locationId: String,
    reviewId: String,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val review by viewModel.review
    Text(text = "locationId: $locationId, reviewId: $reviewId")

}