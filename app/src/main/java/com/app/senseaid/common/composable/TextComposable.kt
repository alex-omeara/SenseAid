package com.app.senseaid.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.senseaid.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.storage.StorageReference

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
        fontWeight = FontWeight.Bold,
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
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
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
//            .border(2.dp, Color.Black),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_round_star_24),
            contentDescription = stringResource(R.string.star_desc),
            tint = Color.Unspecified
        )
        val reviewNum = if (totalReviews != null) { "($totalReviews)" } else { "" }
        Text(text = "$averageRating $reviewNum")
    }
}