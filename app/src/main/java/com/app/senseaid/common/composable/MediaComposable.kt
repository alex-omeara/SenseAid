package com.app.senseaid.common.composable

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.senseaid.R

@Composable
fun UploadMedia(
    modifier: Modifier,
    context: Context,
    contentColor: Color,
    shape: Shape,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    @StringRes mediaText: Int,
    setUploadedFile: (Uri, Context) -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            it?.run { setUploadedFile(it, context) } ?: Log.i("AddReviewContentScreen", "could not get valid file")
        }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable(
                role = Role.Button
            ) {
                launcher.launch("audio/mpeg")
            },
        color = contentColor,
        shape = shape,
        shadowElevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(iconDescription)
            )
            SmallTextTitle(
                modifier = modifier.fillMaxWidth().padding(5.dp),
                text = stringResource(mediaText),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AudioPlayer(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    IconToggleButton(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = IconButtonDefaults.iconToggleButtonColors(
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.tertiaryContainer,
            checkedContainerColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        var icon = R.drawable.ic_baseline_play_arrow_24
        if (checked) icon = R.drawable.ic_baseline_pause_24
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "play arrow"
        )
    }
}