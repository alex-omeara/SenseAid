package com.app.senseaid.screens.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.senseaid.R
import com.app.senseaid.model.Review
import com.app.senseaid.common.composable.SmallTextTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review,
    onReviewPress: () -> Unit
) {
    Card( // TODO: Make into expandable card?
        modifier = modifier.padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onReviewPress
    ) {
        // TODO: add profile image
        Column(modifier = modifier.padding(12.dp)) {
            SmallTextTitle(modifier = modifier, text = review.author)
            Text(text = "${review.rating} / 5")
            BasicText(
                text = review.content,
                softWrap = true,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }


    }

}