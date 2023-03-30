package com.app.senseaid.screens.review

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.common.composable.AudioPlayer
import com.app.senseaid.common.composable.ReviewTimeSinceText
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
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .padding(12.dp)
                    .weight(3f)
            ) {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallTextTitle(modifier = modifier.heightIn(max = 20.dp), text = review.author)
                    if (review.timestamp != null) {
                        ReviewTimeSinceText(
                            modifier = modifier.padding(5.dp),
                            timestamp = review.timestamp,
                            getTimeSinceReview = viewModel::getTimeSinceReview,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Text(text = "${review.rating.toInt()} / 5")
                BasicText(
                    text = review.content,
                    softWrap = true,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (review.sound_recording != null) {
                val soundFileName = viewModel.getFileName(Uri.parse(review.sound_recording), context, false)
                AudioPlayer(
                    modifier = modifier,
                    checked = (viewModel.isPlaying && !viewModel.isPaused),
                    onCheckedChange = {
                        if (it) viewModel.startPlayer(context, "/audio/${review.id}-$soundFileName")
                        else viewModel.pausePlayer()
                    }
                )
            }
        }


    }

}