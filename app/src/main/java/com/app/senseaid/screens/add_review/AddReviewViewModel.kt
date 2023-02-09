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
import com.app.senseaid.model.Tags
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.round

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

    fun onSubmit(
        locationId: String,
        totalRatings: String,
        avgRating: String,
        author: String,
        navToScreen: () -> Unit
    ) {
        launchCatching {
            val formattedLocationId = locationId.substring(1, locationId.length - 1)
            val formattedTotalRatings = totalRatings.substring(1, totalRatings.length - 3).toInt()
            firestoreRepository.addReview(
                author = author,
                rating = rating.toDouble(),
                tags = selectedTags.filter { (_, value) -> value }.keys.map { it.toString() },
                content = reviewContentText,
                locationId = formattedLocationId
            )
            firestoreRepository.updateLocationField(
                locationId = formattedLocationId,
                field = "avgRating",
                value = getNewAvgRating(
                    newRating = rating.toDouble(),
                    totalRatings = formattedTotalRatings,
                    avgRating = avgRating.substring(1, avgRating.length - 1).toDouble()
                )
            )
            firestoreRepository.updateLocationField(
                locationId = formattedLocationId,
                field = "totalReviews",
                value = formattedTotalRatings + 1
            )
        }
        navToScreen()
    }

    fun onTagSelect(tag: Tags) {
        selectedTags[tag] = selectedTags[tag] == false || selectedTags[tag] == null
        Log.i("tag state", "$tag : ${selectedTags[tag]}")
        round(2.592)
    }

    private fun getNewAvgRating(
        newRating: Double,
        totalRatings: Int,
        avgRating: Double
    ): Double {
        val newAvg = avgRating + ((newRating - avgRating) / (totalRatings + 1))
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(newAvg).toDouble()
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