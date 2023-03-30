package com.app.senseaid.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.senseaid.R
import com.google.firebase.Timestamp

@Composable
fun TextTitle(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign,
        color = color
    )
}

@Composable
fun SmallTextTitle(
    modifier: Modifier,
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall,
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun RatingText(
    modifier: Modifier,
    averageRating: Double,
    totalReviews: Int?,
) {
    Row(
        modifier = modifier
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_round_star_24),
            contentDescription = stringResource(R.string.star_desc),
            tint = Color.Unspecified
        )
        val reviewNum = if (totalReviews != null) {
            "($totalReviews)"
        } else {
            ""
        }
        Text(text = "$averageRating $reviewNum")
    }
}

@Composable
fun ReviewTimeSinceText(
    modifier: Modifier = Modifier,
    timestamp: Timestamp,
    getTimeSinceReview: (Timestamp) -> String,
    style: TextStyle = LocalTextStyle.current
) {
    val timeSinceReview = getTimeSinceReview(timestamp)
    val text = if (timeSinceReview.isEmpty()) {
        " - Just now"
    } else {
        " - $timeSinceReview ago"
    }
    Text(
        modifier = modifier,
        text = text,
        color = Color.Unspecified.copy(0.5f),
        fontStyle = FontStyle(1),
        style = style
    )
}