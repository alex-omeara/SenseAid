package com.app.senseaid.common.composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.security.Permission

@Composable
fun UploadMedia(
    modifier: Modifier,
    contentColor: Color,
    shape: Shape,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    @StringRes mediaAction: Int,
    setSoundUri: (Uri) -> Unit
) {
    val permission = Manifest.permission.RECORD_AUDIO
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("permission", "granted")
            } else {
                Log.i("permission", "denied")
            }
        }
//    val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        MediaRecorder(LocalContext.current)
//    } else {
//        MediaRecorder()
//    }
//    val recorderSampleRate = 44100
//    val testFile = File.createTempFile("soundRecording", null, LocalContext.current.cacheDir)
//    mediaRecorder.setAudioSamplingRate(recorderSampleRate)
//    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
//    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//    mediaRecorder.setOutputFile(testFile)
//    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB)

//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = { uri: Uri? ->
//            Log.i("d","uri:" + uri.toString())
//            if (uri != null) {
//                setSoundUri(uri)
//            }
//        })
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable(
                role = Role.Button
            ) {
//                checkAndRequestPermission(LocalContext.current, launcher, permission)
//                mediaRecorder.prepare()
//                mediaRecorder.start()
            },
        // launcher.launch("image/*")
        color = contentColor,
        shape = shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(iconDescription)
            )
            SmallTextTitle(modifier = modifier, text = stringResource(mediaAction))
        }
    }
}