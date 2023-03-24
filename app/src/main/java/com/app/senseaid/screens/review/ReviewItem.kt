package com.app.senseaid.screens.review

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.model.Review
import com.app.senseaid.common.composable.SmallTextTitle
import com.app.senseaid.screens.location.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review,
    viewModel: LocationViewModel = hiltViewModel(),
    context: Context,
    onReviewPress: () -> Unit
) {
    Card(
        modifier = modifier.padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        onClick = onReviewPress,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row {
            Column(modifier = modifier
                .padding(12.dp)
                .weight(3f)) {
                SmallTextTitle(modifier = modifier, text = review.author)
                Text(text = "${review.rating} / 5")
                BasicText(
                    text = review.content,
                    softWrap = true,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (review.sound_recording != null) {
                Log.i("ReviewItem", "sound url: ${review.sound_recording}")
                IconButton(modifier = modifier.weight(1f),
                    onClick = {
                        viewModel.startPlaying(context, "/audio/${review.id}-sound.mp3")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = "play arrow"
                    )
                }
            }
        }


    }

}