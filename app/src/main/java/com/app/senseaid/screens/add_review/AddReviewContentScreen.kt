package com.app.senseaid.screens.add_review

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.common.composable.*
import com.app.senseaid.model.Tags
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddReviewContentScreen(
    modifier: Modifier = Modifier,
    viewModel: AddReviewViewModel = hiltViewModel(),
    locationId: String,
    totalRatings: String,
    avgRating: String,
    onBackPress: () -> Unit,
    onSubmitPress: () -> Unit
) {
    Scaffold(
        topBar = {
            BasicActionToolbar(
                title = R.string.add_review_screen_title,
                navigationIcon = R.drawable.ic_baseline_arrow_back_24,
                navigationDescription = R.string.back_button,
                modifier = modifier,
                navigationAction = onBackPress
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(
                top = paddingValues.calculateTopPadding(),
                start = 15.dp,
                end = 15.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextTitle(text = stringResource(R.string.add_review_title))

            RatingBar(
                value = viewModel.rating,
                config = RatingBarConfig().size(48.dp),
                onValueChange = { newRating -> viewModel.updateRating(newRating) },
                onRatingChanged = { Log.d("RATING", "onRatingChanged: $it") }
            )
            FlowRow() {
                BasicButton(text = R.string.select_tags, modifier = modifier) {
                    viewModel.onPopup()
                }
                if (viewModel.popupState) {
                    Popup(
                        onDismissRequest = { viewModel.onPopup() },
                        popupPositionProvider = CenterWindowOffsetPopupPositionProvider(),
                    ) {
                        Surface(
                            modifier = modifier.padding(8.dp),
                            shape = RoundedCornerShape(32.dp),
                            color = Color(0xCCEEEEEE),
                            shadowElevation = 4.dp
                        ) {
                            FlowRow(
                                horizontalArrangement = Arrangement.Start
                            ) {
                                val selectedTags = viewModel.selectedTags
                                enumValues<Tags>().forEach { tag ->
                                    val color =
                                        if (selectedTags[tag] == true) Color.Green else Color.Blue
                                    Button(
                                        modifier = modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 2.dp
                                        ),
                                        colors = ButtonDefaults.buttonColors(containerColor = color),
                                        onClick = { viewModel.onTagSelect(tag) }
                                    ) {
                                        Text(text = tag.toString())
                                    }
                                }
                            }
                        }
                    }
                }
                if (viewModel.selectedTags.containsValue(true)) {
                    viewModel.selectedTags.forEach { (key, value) ->
                        if (value) {
                            IconTextButton(
                                text = key.toString(),
                                modifier = modifier,
                                iconRes = R.drawable.ic_baseline_close_24,
                                iconDesc = R.string.remove
                            ) {

                            }
                        }
                    }
                }
            }
            SmallTextTitle(modifier = modifier, text = stringResource(R.string.add_sound_recording))
            UploadMedia(
                modifier = modifier,
                contentColor = Color.LightGray,
                shape = RoundedCornerShape(10.dp),
                icon = R.drawable.ic_baseline_cloud_upload_24,
                iconDescription = R.string.upload,
                mediaAction = R.string.click_to_upload
            )
            CharRemainingField(
                text = R.string.add_review_content,
                charsRemaining = R.string.chars_remaining,
                value = viewModel.reviewContentText,
                modifier = modifier,
                charsAdded = viewModel.charsAdded,
                onNewValue = viewModel::updateReviewContent
            )
            // TODO: Move to bottom of screen (custom layout not BottomAppBar)
            BasicButton(text = R.string.submit, modifier = modifier.fillMaxWidth()) {
                viewModel.onSubmit(
                    locationId = locationId,
                    totalRatings = totalRatings,
                    avgRating = avgRating,
                    author = "me",
                    navToScreen = onSubmitPress
                )
            }
        }
    }
}