package com.app.senseaid.screens.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.Routes.LOCATION_ID
import com.app.senseaid.Routes.REVIEW_ID
import com.app.senseaid.Routes.REVIEW_SCREEN
import com.app.senseaid.domain.model.Review
import com.app.senseaid.screens.common.composable.SmallTextTitle
import com.app.senseaid.screens.home.HomeViewModel
import com.app.senseaid.screens.location.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
//    viewModel: LocationViewModel = hiltViewModel(),
    review: Review,
    locationId: String,
    onReviewClicked: (String) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onReviewClicked("${REVIEW_SCREEN}?${LOCATION_ID}={$locationId}&${REVIEW_ID}={${review.id}}") }
    ) {
        Row() { // TODO: add profile image
            Column() {
                SmallTextTitle(title = review.title)
                BasicText(text = review.content, softWrap = true)
            }
        }

    }

}