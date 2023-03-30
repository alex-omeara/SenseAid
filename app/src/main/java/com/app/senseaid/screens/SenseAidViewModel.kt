package com.app.senseaid.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.senseaid.model.repository.StorageRepository
import com.google.firebase.Timestamp
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

abstract class SenseAidViewModel : ViewModel() {
    @Inject
    protected lateinit var storageRepository: StorageRepository

    private var mediaPlayer: MediaPlayer? = null

    var isPlaying by mutableStateOf(false)
        private set

    var isPaused by mutableStateOf(false)
        private set

    // TODO: add proper catching https://github.com/FirebaseExtended/make-it-so-android/blob/55b8504db5dc588dbe82787c4d3abb8be37a94aa/app/src/main/java/com/example/makeitso/screens/MakeItSoViewModel.kt
    fun launchCatching(snackbar: Boolean = false, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(block = block)

    fun checkAndRequestPermission(
        context: Context,
        permissions: Array<String>,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ): Boolean {
        return if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            true
        } else {
            launcher.launch(permissions)
            false
        }
    }

    fun getLocationImage(imgPath: String): StorageReference =
        if (imgPath == "") {
            storageRepository.getFileFromStorage("placeholder-image.jpg")
        } else {
            storageRepository.getFileFromStorage(imgPath)
        }

    fun startPlayer(context: Context, filePath: String) {
        Log.i("MediaPlayer", "Playing: $filePath")
        if (isPaused) {
            mediaPlayer!!.start()
            isPaused = !isPaused
        } else {
            storageRepository.getFileDownloadUri(filePath).addOnSuccessListener { uri ->
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource(context, uri)
                mediaPlayer!!.prepareAsync()
                mediaPlayer!!.setOnPreparedListener { mp ->
                    mp.start()
                    isPlaying = !isPlaying
                }
                mediaPlayer!!.setOnCompletionListener { mp ->
                    mp.release()
                    isPlaying = !isPlaying
                    isPaused = !isPaused
                    mediaPlayer = null
                }
            }
        }
    }

    fun pausePlayer() {
        mediaPlayer!!.pause()
        isPaused = !isPaused
    }

    fun getPlayerDuration() = mediaPlayer?.duration ?: 0

    @SuppressLint("Range")
    fun getFileName(file: Uri, context: Context, isLocal: Boolean = true): String? {
        var result: String? = null
        if (file.scheme == "content" && !isLocal) {
            val cursor = context.contentResolver.query(file, null, null, null)
            cursor.use { c ->
                c?.moveToFirst()
                result = c?.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        if (result == null) {
            result = file.path!!
            var lastIndexChar = '/'
            if (!isLocal) { lastIndexChar = '-' }
            val slice = result!!.lastIndexOf(lastIndexChar)
            if (slice != -1) {
                result = result!!.substring(slice + 1)
            }
        }
        return result
    }

    fun getTimeSinceReview(timestamp: Timestamp): String {
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val timeZone = ZoneId.systemDefault()
        val reviewLocalDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), timeZone)
        val curLocalDateTime = LocalDateTime.now()
        var fromTemp = LocalDateTime.from(reviewLocalDateTime)
        val years = fromTemp.until(curLocalDateTime, ChronoUnit.YEARS)
        fromTemp = fromTemp.plusYears(years)

        val months = fromTemp.until(curLocalDateTime, ChronoUnit.MONTHS)
        fromTemp = fromTemp.plusMonths(months)

        val days = fromTemp.until(curLocalDateTime, ChronoUnit.DAYS)
        fromTemp = fromTemp.plusDays(days)

        val hours = fromTemp.until(curLocalDateTime, ChronoUnit.HOURS)
        fromTemp = fromTemp.plusHours(hours)

        val minutes = fromTemp.until(curLocalDateTime, ChronoUnit.MINUTES)
        fromTemp = fromTemp.plusMinutes(minutes)

        val seconds = fromTemp.until(curLocalDateTime, ChronoUnit.SECONDS)
        fromTemp = fromTemp.plusSeconds(seconds)

        val millis = fromTemp.until(curLocalDateTime, ChronoUnit.MILLIS)
        fromTemp = fromTemp.plusNanos(millis * 1000000)

        return if (years > 0) {
            "$years year${
                if (years > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else if (months > 0) {
            "$months month${
                if (months > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else if (days > 0) {
            "$days day${
                if (days > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else if (hours > 0) {
            "$hours hour${
                if (hours > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else if (minutes > 0) {
            "$minutes minute${
                if (minutes > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else if (seconds > 0) {
            "$seconds second${
                if (seconds > 1) {
                    "s"
                } else {
                    ""
                }
            }"
        } else {
            ""
        }
    }

    companion object {
        private const val TAG = "SenseAidViewModel"
    }
}