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
import androidx.compose.ui.platform.LocalContext
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
    Card( // TODO: Make into expandable card?
        modifier = modifier.padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onReviewPress
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
            Log.i("ReviewItem", "${review.sound_recording}")
            Log.i("ReviewItem", "gs://senseaid-895bb.appspot.com/audio/file_example_MP3_700KB.mp3")
            if (review.sound_recording != null) {
                IconButton(modifier = modifier.weight(1f),
                    onClick = { viewModel.startPlaying(context, review.sound_recording) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = "play arrow"
                    )
                }
            }
        }


    }

}