package com.app.senseaid.screens.location

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.screens.common.composable.LocationImage
import com.app.senseaid.screens.common.composable.TextTitle
import com.app.senseaid.screens.review.ReviewItem

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    locationId: String,
    viewModel: LocationViewModel = hiltViewModel(),
    onReviewClicked: (String) -> Unit,
    onBackPress: () -> Unit
) {
    val location by viewModel.location

    LaunchedEffect(Unit) { viewModel.initialise(locationId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = R.string.back_button.toString()
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Log.i("Add Review", "Add review press") },
                shape = CircleShape,
                containerColor = Color.Cyan
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = R.string.add_review.toString()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            LocationImage(
                modifier = modifier.height(200.dp),
                imgStorageReference = viewModel.getLocationImage(location.imgPath),
                imgDesc = location.imgDesc,
            )
            val reviews = viewModel.reviews.collectAsStateWithLifecycle(emptyList())

            TextTitle(title = location.title, textAlign = TextAlign.Center)
            RatingBar(
                averageRating = location.avgRating ?: -1.0,
                totalReviews = reviews.value.size,
                topTags = location.top_tags
            )
            LazyColumn(modifier = modifier) {
                items(
                    items = reviews.value
                ) { reviewItem ->
                    ReviewItem(
                        review = reviewItem,
                        locationId = location.id,
                        onReviewClicked = onReviewClicked,
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    averageRating: Double,
    totalReviews: Int,
    topTags: List<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_star_24),
                contentDescription = R.string.star_desc.toString(),
                tint = Color.Unspecified
            )
            Text(text = "$averageRating ($totalReviews)")
        }
        if (topTags.isNotEmpty()) {
            Divider(modifier = modifier
                .fillMaxHeight()
                .width(1.dp)
            )
            Text(
                text = topTags[0],
                textAlign = TextAlign.Center
            )
            Divider(modifier = modifier
                .fillMaxHeight()
                .width(1.dp)
            )
            Text(
                text = topTags[1],
                textAlign = TextAlign.Center
            )
        }

    }

}

@Preview
@Composable
fun RatingbarPreview() {
    RatingBar(
        averageRating = 3.2,
        totalReviews = 14,
        topTags = listOf("Bright lights", "Many people")
    )
}