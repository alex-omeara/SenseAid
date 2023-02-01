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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.domain.model.Review
import com.app.senseaid.screens.common.composable.TextTitle
import kotlin.math.floor
import kotlin.math.round

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    locationId: String,
    reviewId: String,
    review: Review = Review(),
    viewModel: ReviewViewModel = hiltViewModel()
) {
    Log.i("Review Screen", "locationId: $locationId, reviewId: $reviewId")
    val review by viewModel.review

    LaunchedEffect(Unit) { viewModel.initialise(locationId, reviewId) }

    Column(modifier = modifier.padding(15.dp)) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = review.author,
            fontWeight = FontWeight.Bold

        )
        TextTitle(title = review.title)
        Row(
            modifier = modifier.fillMaxWidth(),
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
        Text(modifier = modifier, text = review.content)
    }
}

//@Preview
//@Composable
//fun ReviewScreenPreview() {
//    ReviewScreen(
//        locationId = "",
//        reviewId = "",
//        review = Review(
//            id = "fakeId",
//            title = "Title",
//            author = "Author",
//            rating = 3.5,
//            content = "Donec eget felis gravida tellus molestie semper. Donec ac luctus nulla. Donec urna elit, aliquet nec ultrices non, lacinia sit amet turpis. Suspendisse lobortis magna eu tempus vulputate. Proin id dui dui. Morbi nec nisl ipsum. Proin sit amet consectetur erat, ac sodales ipsum. Aenean porttitor sollicitudin ex et vehicula."
//        )
//    )
//}