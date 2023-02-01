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
import com.app.senseaid.Routes.LOCATION_ID
import com.app.senseaid.Routes.REVIEW_ID
import com.app.senseaid.Routes.REVIEW_SCREEN
import com.app.senseaid.domain.model.Review
import com.app.senseaid.screens.common.composable.SmallTextTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review,
    locationId: String,
    onReviewClicked: (String) -> Unit
) {
    Card( // TODO: Make into expandable card?
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onReviewClicked("${REVIEW_SCREEN}?${LOCATION_ID}={${locationId}}&${REVIEW_ID}={${review.id}}") }
    ) {
        Row() { // TODO: add profile image
            Column() {
                SmallTextTitle(title = review.title)
                Text(text = review.rating.toString())
                BasicText(text = review.content, softWrap = true)
            }
        }

    }

}