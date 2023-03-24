package com.app.senseaid.screens.review

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.common.composable.TextTitle

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
                .padding(paddingValues)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = review.author,
                fontWeight = FontWeight.Bold

            )
            TextTitle(modifier = modifier.fillMaxWidth(), text = review.title)
            Row(
                modifier = modifier.fillMaxWidth(),
            ) {
                repeat(review.rating.toInt()) {
                    Icon(
                        modifier = modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_round_star_24),
                        contentDescription = stringResource(R.string.star_desc),
                        tint = Color.Unspecified
                    )
                }
            }
            Text(modifier = modifier, text = review.content)
        }
    }
}