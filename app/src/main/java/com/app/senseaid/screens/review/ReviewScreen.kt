package com.app.senseaid.screens.review

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.screens.common.composable.TextTitle
import kotlin.math.floor
import kotlin.math.round

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    locationId: String,
    reviewId: String,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    Log.i("Review Screen", "locationId: $locationId, reviewId: $reviewId")
    val review by viewModel.review

    LaunchedEffect(Unit) { viewModel.initialise(locationId, reviewId) }

    Column() {
        Row() {
            Column(modifier = modifier.fillMaxWidth(0.3f)) {
                // TODO Add profile image
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = review.author,
                    textAlign = TextAlign.Center
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(floor(review.rating).toInt()) {
                        Icon(
                            modifier = modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.ic_round_star_24),
                            contentDescription = "star",
                            tint = Color.Unspecified
                        )
                    }
                    if (review.rating % 1 != 0.0) {
                        val starsRemaining = round((review.rating - floor(review.rating)) * 2) / 2.0
                        if (starsRemaining != 0.0) {
                            val starResourceId: Int =
                                if (starsRemaining == 1.0) R.drawable.ic_round_star_24 else R.drawable.ic_round_star_half_24
                            Icon(
                                modifier = modifier.size(48.dp),
                                painter = painterResource(id = starResourceId),
                                contentDescription = "star",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
                TextTitle(title = review.title)
            }
        }
        Text(modifier = modifier.padding(horizontal = 10.dp),text = review.content)
    }
}