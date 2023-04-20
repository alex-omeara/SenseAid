package com.app.senseaid.screens.location

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.common.composable.*
import com.app.senseaid.model.SensoryTags
import com.app.senseaid.model.SortDirection
import com.app.senseaid.screens.review.ReviewItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    locationId: String,
    viewModel: LocationViewModel = hiltViewModel(),
    onReviewClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddReviewClick: (String) -> Unit
) {
    val location by viewModel.location

    LaunchedEffect(Unit) { viewModel.initialise(locationId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            BasicActionBar(
                actionIcon = R.drawable.ic_baseline_add_24,
                actionDesc = R.string.add_review,
                fabAction = { viewModel.onAddReview(location, onAddReviewClick) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            GlideImage(
                modifier = modifier.height(200.dp),
                model = viewModel.getLocationImage(location.imgPath),
                contentDescription = location.imgDesc,
                contentScale = ContentScale.FillWidth
            )
            val reviews = viewModel.reviews.value.collectAsStateWithLifecycle(emptyList())

            TextTitle(
                modifier = modifier.fillMaxWidth(),
                text = location.title,
                textAlign = TextAlign.Center
            )
            RatingInfo(
                averageRating = location.avgRating,
                totalReviews = reviews.value.size,
                topTags = location.top_tags
            )
            Divider()
            Box(
                modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { viewModel.toggleSortReviews() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    expanded = viewModel.isSorting,
                    onDismissRequest = { viewModel.toggleSortReviews() },
                    offset = DpOffset(260.dp, 0.dp)
                ) {
                    enumValues<SortDirection>().forEach { direction ->
                        if (direction != SortDirection.ASCENDING && direction != SortDirection.DESCENDING) {
                            DropdownMenuItem(
                                text = { Text(text = direction.toString()) },
                                onClick = {
                                    viewModel.toggleSortReviews()
                                    viewModel.launchCatching { viewModel.sortReviews(direction) }
                                }
                            )
                        }
                    }

                }
            }
            val listState = rememberLazyListState()
            LaunchedEffect(key1 = reviews.value.firstOrNull()) { listState.scrollToItem(0) }
            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 10.dp),
                state = listState
            ) {
                items(
                    items = reviews.value,
                    key = { it.id }
                ) { reviewItem ->
                    if (reviewItem.id == "oXIFgDsFJ1n70beNUE3j") {
                        Log.i("ReviewItem", reviewItem.timestamp.toString())
                    }
                    ReviewItem(
                        review = reviewItem,
                        context = LocalContext.current
                    ) {
                        viewModel.onReviewPress(
                            location.id,
                            reviewItem.id,
                            onReviewClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RatingInfo(
    modifier: Modifier = Modifier,
    averageRating: Double,
    totalReviews: Int,
    topTags: List<SensoryTags>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingText(
            modifier = modifier,
            averageRating = averageRating,
            totalReviews = totalReviews
        )
        if (topTags.isNotEmpty()) {
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = topTags[0].toString(),
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = topTags[1].toString(),
                textAlign = TextAlign.Center
            )
        }

    }

}