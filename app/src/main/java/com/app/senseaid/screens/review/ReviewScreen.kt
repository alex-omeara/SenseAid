package com.app.senseaid.screens.review

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.domain.model.Review
import com.app.senseaid.screens.common.composable.TextTitle
import kotlin.math.floor
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel(),
    locationId: String,
    reviewId: String,
    review: Review = Review(),
    onBackPress: () -> Unit
) {
    Log.i("Review Screen", "locationId: $locationId, reviewId: $reviewId")
    val review by viewModel.review

    LaunchedEffect(Unit) { viewModel.initialise(locationId, reviewId) }

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
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
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
                        contentDescription = R.string.star_desc.toString(),
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
                            contentDescription = R.string.star_desc.toString(),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
            Text(modifier = modifier, text = review.content)
        }
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