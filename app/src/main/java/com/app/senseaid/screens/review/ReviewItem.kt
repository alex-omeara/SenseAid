package com.app.senseaid.screens.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.model.Review
import com.app.senseaid.common.composable.SmallTextTitle
import com.app.senseaid.screens.location.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel(),
    review: Review,
    locationId: String,
    onReviewPress: (String) -> Unit
) {
    Card( // TODO: Make into expandable card?
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { viewModel.onReviewPress(locationId, review.id, onReviewPress) }
    ) {
        Row() { // TODO: add profile image
            Column() {
                SmallTextTitle(modifier = modifier, text = review.title)
                Text(text = review.rating.toString())
                BasicText(text = review.content, softWrap = true)
            }
        }

    }

}