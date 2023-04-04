package com.app.senseaid.screens.add_review

import android.content.Context
import android.media.MediaRecorder
import android.media.MediaRecorder.OutputFormat
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.app.senseaid.model.FileType
import com.app.senseaid.model.SensoryTags
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    val firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    var rating by mutableStateOf(0f)
        private set

    var reviewAuthor by mutableStateOf("")
        private set

    var reviewContentText by mutableStateOf("")
        private set

    var charsAdded by mutableStateOf(0)
        private set

    val selectedTags = mutableStateMapOf<SensoryTags, Boolean>()

    var popupState by mutableStateOf(false)
        private set

    var recordTime by mutableStateOf(0L)
        private set

    private val timer = object : CountDownTimer(RECORD_LIMIT.toLong(), COUNTER_STEP) {
        override fun onTick(millisUntilFinished: Long) {
            Log.i(TAG, (millisUntilFinished / COUNTER_STEP).toString())
            recordTime = ((RECORD_LIMIT - millisUntilFinished) + 1000) / COUNTER_STEP
        }

        override fun onFinish() {
            Log.i(TAG, "timer finished")
            stopRecording()
        }
    }
    private var recorder: MediaRecorder? = null

    var isRecorded by mutableStateOf(false)
        private set

    private var filePath: Uri? = null
    private var fileName: String? = null

    fun setUploadedFile(file: Uri, context: Context) {
        filePath = file
        fileName = getFileName(file, context)
        Log.i("", "name: $fileName, path: $filePath")
    }

    fun onPopup() {
        popupState = !popupState
    }

    fun updateReviewContent(input: String) {
        if (input.length <= 400) {
            reviewContentText = input
            charsAdded = input.length
        }
    }

    fun updateReviewAuthor(input: String) {
        if (input.length <= 20) {
            reviewAuthor = input
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
            val formattedTotalRatings = totalRatings.substring(1, totalRatings.length - 1).toInt()
            val reviewId = firestoreRepository.addReview(
                author = author,
                rating = rating.toDouble(),
                tags = selectedTags.filter { (_, value) -> value }.keys.map { it.toString() },
                content = reviewContentText,
                locationId = formattedLocationId,
            )
            if (filePath != null && fileName != null) {
                Log.i("AddReviewViewModel", filePath!!.toString())
                storageRepository.uploadFile(filePath!!, reviewId, FileType.AUDIO, fileName!!)
            }
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

    fun onTagSelect(tag: SensoryTags) {
        selectedTags[tag] = selectedTags[tag] == false || selectedTags[tag] == null
        Log.d(TAG, "$tag : ${selectedTags[tag]}")
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

    fun onRecorded() {
        isRecorded = !isRecorded
    }

    fun getPermissions(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        if (checkAndRequestPermission(context, permissions, launcher)) {
            filePath = Uri.parse("${context.cacheDir}/sound.mp3")
            startRecording(context)
        }
    }

    private fun startRecording(context: Context) {
        try {
            recorder = MediaRecorder(context).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(OutputFormat.AAC_ADTS)
                setOutputFile(filePath.toString())
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                prepare()
                start()
                timer.start()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to prepare MediaRecorder $e")
        } catch (e: IllegalStateException) {
            Log.e(TAG, e.stackTraceToString())
        }
    }

    fun stopRecording() {
        try {
            if (recorder != null) {
                recorder!!.stop()
                recorder!!.reset()
                recorder!!.release()
                recorder = null
                timer.cancel()
                onRecorded()
                Log.i(TAG, "recording stopped")
                Log.i(TAG, "file recorded at $filePath")
            }
        } catch (e: IllegalStateException) {
            Log.e(TAG, "failed to stop recording: ${e.stackTraceToString()}")
        }
    }

    companion object {
        private const val TAG = "AddReviewViewModel"
        private const val RECORD_LIMIT = 5000
        private const val COUNTER_STEP = 1000L
    }
}
