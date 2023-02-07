package com.app.senseaid.screens.add_review

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.app.senseaid.model.Review
import com.app.senseaid.model.Tags
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    var rating by mutableStateOf(0f)
        private set

    var reviewContentText by mutableStateOf("")
        private set

    var charsAdded by mutableStateOf(0)
        private set

    val selectedTags = mutableStateMapOf<Tags, Boolean>()

    var popupState by mutableStateOf(false)
    private set

    fun onPopup() {
        popupState = !popupState
    }

    fun updateReviewContent(input: String) {
        if (input.length <= 400) {
            reviewContentText = input
            charsAdded = input.length
        }
    }

    fun updateRating(newRating: Float) {
        rating = newRating
    }

    fun onSubmitReviewContent(
        locationId: String,
        author: String,
        navToScreen: () -> Unit
    ) {
        launchCatching {
            val review = Review(
                author = author,
                rating = rating.toDouble(),
                tags = selectedTags.keys.map { it.toString() },
                content = reviewContentText
            )
            Log.i("new review", review.id)
//            firestoreRepository.addReview(review, locationId)
        }
        navToScreen()
    }

    fun onTagSelect(tag: Tags) {
        selectedTags[tag] = selectedTags[tag] == false || selectedTags[tag] == null
        Log.i("tag state", "$tag : ${selectedTags[tag]}")
    }
}

class CenterWindowOffsetPopupPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0,
    ) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y,
        )
    }

}