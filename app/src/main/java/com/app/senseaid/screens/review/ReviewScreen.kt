package com.app.senseaid.screens.review

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.common.composable.AudioPlayer
import com.app.senseaid.common.composable.ReviewTimeSinceText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel(),
    locationId: String,
    reviewId: String,
    onBackPress: () -> Unit
) {
    Log.i("Review Screen", "locationId: $locationId, reviewId: $reviewId")
    val review by viewModel.review

    LaunchedEffect(Unit) { viewModel.initialise(locationId, reviewId) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    top = paddingValues
                        .calculateTopPadding()
                        .plus(16.dp), start = 16.dp, end = 16.dp
                )
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    modifier = modifier,
                    text = review.author,
                    fontWeight = FontWeight.Bold
                )
                if (review.timestamp != null) {
                    ReviewTimeSinceText(
                        timestamp = review.timestamp!!,
                        getTimeSinceReview = viewModel::getTimeSinceReview
                    )
                }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var i = 0
                repeat(5) {
                    val color =
                        if (i < review.rating) Color.Unspecified else MaterialTheme.colorScheme.outline.copy(
                            0.2f
                        )
                    Icon(
                        modifier = modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_round_star_24),
                        contentDescription = stringResource(R.string.star_desc),
                        tint = color
                    )
                    i++
                }
                if (review.sound_recording != null) {
                    val context = LocalContext.current
                    val soundFileName = viewModel.getFileName(Uri.parse(review.sound_recording), context, false)
                    Spacer(modifier = modifier.size(16.dp))
                    AudioPlayer(
                        checked = (viewModel.isPlaying && !viewModel.isPaused),
                        onCheckedChange = {
                            if (it) viewModel.startPlayer(context, "/audio/${review.id}-$soundFileName")
                            else viewModel.pausePlayer()
                        }
                    )
                    AnimatedVisibility(visible = (viewModel.isPlaying || viewModel.isPaused)) {
                        Text(text = "${viewModel.getPlayerDuration() / 1000}s")
                    }
                }
            }
            Text(modifier = modifier, text = review.content)
        }
    }
}