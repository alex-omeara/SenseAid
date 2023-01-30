package com.app.senseaid.screens.location

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.screens.common.composable.TextTitle
import com.app.senseaid.screens.review.ReviewItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    onReviewClicked: (String) -> Unit,
    locationId: String,
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val location by viewModel.location
    
    LaunchedEffect(Unit) { viewModel.initialise(locationId) }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            GlideImage(
                model = viewModel.getLocationImage(location.imgPath),
                contentDescription = location.imgDesc,
            )
            TextTitle(title = location.title)
            RatingBar(modifier, location.avgRating ?: -1.0)
            val reviews = viewModel.reviews.collectAsStateWithLifecycle(emptyList())
            LazyColumn(modifier = modifier) {
                items(
                    items = reviews.value
                ) {
                    reviewItem ->
                    ReviewItem(
                        review = reviewItem,
                        locationId = locationId.substring(1, locationId.length - 1),
                        onReviewClicked = onReviewClicked
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(modifier: Modifier = Modifier, averageRating: Double) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(id = R.drawable.round_star_rate_24),
            contentDescription = "star",
            tint = Color.Unspecified
        )
        Text(text = "3.6 (28)")
        Text(text = "Bright Lights")
        Text(text = "Low Sound")

    }
    
}