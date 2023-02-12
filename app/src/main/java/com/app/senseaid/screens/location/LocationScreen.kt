package com.app.senseaid.screens.location

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.common.composable.*
import com.app.senseaid.model.SortDirection
import com.app.senseaid.screens.review.ReviewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    locationId: String,
    viewModel: LocationViewModel = hiltViewModel(),
    onReviewPress: (String) -> Unit,
    onBackPress: () -> Unit,
    onAddReviewPress: (String) -> Unit
) {
    val location by viewModel.location

    LaunchedEffect(Unit) { viewModel.initialise(locationId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
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
                fabAction = { viewModel.onAddReview(location, onAddReviewPress) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            LocationImage(
                modifier = modifier.height(200.dp),
                imgStorageReference = viewModel.getLocationImage(location.imgPath),
                imgDesc = location.imgDesc,
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
            Box() {
                SmallTextTitle(
                    modifier = modifier.clickable(
                        role = Role.DropdownList,
                        onClickLabel = stringResource(id = R.string.sort_by)
                    ) { viewModel.toggleSortReviews() },
                    text = stringResource(id = R.string.sort_by),
                    textAlign = TextAlign.End
                )
                DropdownMenu(
                    expanded = viewModel.isSortReviews,
                    onDismissRequest = {
                        viewModel.toggleSortReviews()
                        viewModel.launchCatching {
                            viewModel.sortReviews()
                        }
                    },
                    offset = DpOffset(260.dp, 0.dp)
                ) {
                    Sort(
                        modifier = modifier,
                        sortSelection = viewModel.sortSelection,
                        excludeDirection = listOf(SortDirection.ASCENDING, SortDirection.DESCENDING),
                        onClick = viewModel::onSortByPress
                    )
                }
            }
            val listState = rememberLazyListState()
            LaunchedEffect(key1 = reviews.value.firstOrNull()) { listState.scrollToItem(0) }
            LazyColumn(modifier = modifier.padding(horizontal = 10.dp), state = listState) {
                items(
                    items = reviews.value,
                    key = { it.id }
                ) { reviewItem ->
                    ReviewItem(
                        review = reviewItem,
                    ) {
                        viewModel.onReviewPress(
                            location.id,
                            reviewItem.id,
                            onReviewPress
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
    topTags: List<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_star_24),
                contentDescription = stringResource(R.string.star_desc),
                tint = Color.Unspecified
            )
            Text(text = "$averageRating ($totalReviews)")
        }
        if (topTags.isNotEmpty()) {
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = topTags[0],
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                text = topTags[1],
                textAlign = TextAlign.Center
            )
        }

    }

}

@Preview
@Composable
fun RatingbarPreview() {
    RatingInfo(
        averageRating = 3.2,
        totalReviews = 14,
        topTags = listOf("Bright lights", "Many people")
    )
}