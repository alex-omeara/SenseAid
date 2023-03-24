package com.app.senseaid.screens.add_review

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import android.view.KeyEvent
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.common.composable.*
import com.app.senseaid.common.popup.CenterWindowOffsetPositionProvider
import com.app.senseaid.model.LocationTags
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
            BasicNavToolbar(
                title = R.string.add_review_screen_title,
                navigationIcon = R.drawable.ic_baseline_arrow_back_24,
                navigationDescription = R.string.back_button,
                modifier = modifier,
                navigationAction = onBackPress
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 15.dp,
                    end = 15.dp
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextTitle(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.add_review_title)
            )

            RatingBar(
                value = viewModel.rating,
                config = RatingBarConfig().size(48.dp),
                onValueChange = { newRating -> viewModel.updateRating(newRating) },
                onRatingChanged = { Log.d("RATING", "onRatingChanged: $it") }
            )

            TagsRow(modifier = modifier)

            UploadMedia(
                modifier = modifier,
                contentColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(10.dp),
                icon = R.drawable.ic_baseline_cloud_upload_24,
                iconDescription = R.string.upload,
                mediaAction = R.string.click_to_upload,
                setSoundUri = viewModel::setSoundUri
            )
            RecordButton(
                iconRes = R.drawable.ic_baseline_mic_24,
                iconDesc = R.string.mic,
            )
            val (focusRequester) = FocusRequester.createRefs()
            OutlinedTextField(
                modifier = modifier.fillMaxWidth().onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        focusRequester.requestFocus()
                        true
                    }
                    false
                },
                placeholder = { Text(text = stringResource(id = R.string.add_review_author)) },
                value = viewModel.reviewAuthor,
                onValueChange = { viewModel.updateReviewAuthor(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusRequester.requestFocus() })
            )
            OutlinedTextField(
                modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
                value = viewModel.reviewContentText,
                onValueChange = { viewModel.updateReviewContent(it) },
                placeholder = { Text(stringResource(R.string.add_review_content)) },
                supportingText = { Text("${400 - viewModel.charsAdded} ${stringResource(R.string.chars_remaining)}") },
                minLines = 7,
                maxLines = 7,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { viewModel.onSubmit(
                    locationId = locationId,
                    totalRatings = totalRatings,
                    avgRating = avgRating,
                    author = viewModel.reviewAuthor,
                    navToScreen = onSubmitPress
                ) })
            )
            // TODO: Move to bottom of screen (custom layout not BottomAppBar)
            BasicButton(text = R.string.submit, modifier = modifier.fillMaxWidth()) {
                viewModel.onSubmit(
                    locationId = locationId,
                    totalRatings = totalRatings,
                    avgRating = avgRating,
                    author = viewModel.reviewAuthor,
                    navToScreen = onSubmitPress
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsRow(
    modifier: Modifier,
    viewModel: AddReviewViewModel = hiltViewModel()
) {
    FlowRow {
        BasicButton(text = R.string.select_tags, modifier = modifier) { viewModel.onPopup() }
        if (viewModel.popupState) {
            Popup(
                onDismissRequest = { viewModel.onPopup() },
                popupPositionProvider = CenterWindowOffsetPositionProvider(),
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
                        enumValues<LocationTags>().forEach { tag ->
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
                        text = { Text(text = key.toString()) },
                        modifier = modifier,
                        iconRes = R.drawable.ic_baseline_close_24,
                        iconDesc = R.string.remove
                    ) {

                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RecordButton(
    modifier: Modifier = Modifier,
    viewModel: AddReviewViewModel = hiltViewModel(),
    @DrawableRes iconRes: Int,
    @StringRes iconDesc: Int,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Log.i("IR", "${viewModel.isRecorded}")
    if (!viewModel.isRecorded) {
        val context = LocalContext.current
        val permissions =
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_AUDIO)
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
                val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (areGranted) {
                    Log.i("AddReviewContentScreen", "permission granted")
                } else {
                    Log.i("AddReviewContentScreen", "permission denied")
                }
            }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color.Red else Color.Transparent
        if (isPressed) {
            viewModel.checkAndRequestPermission(context, permissions, launcher)
        } else {
            viewModel.stopRecording()
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${stringResource(id = R.string.record_audio)}:")
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            TextButton(
                onClick = { },
                modifier = modifier,
                interactionSource = interactionSource,
                colors = ButtonDefaults.textButtonColors(containerColor = color)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = stringResource(id = iconDesc)
                )
                AnimatedVisibility(visible = isPressed) {
                    Text(text = "${viewModel.recordTime}s / 5s")
                }
            }
        }
    } else {
        TextButton(onClick = { viewModel.onRecorded() }) {
            Text(text = stringResource(id = R.string.record_audio_again))
        }
    }
}