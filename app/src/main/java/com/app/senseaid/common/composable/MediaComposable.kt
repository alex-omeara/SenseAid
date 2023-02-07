package com.app.senseaid.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun UploadMedia(
    modifier: Modifier,
    contentColor: Color,
    shape: Shape,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    @StringRes mediaAction: Int
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp),
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
            SmallTextTitle(modifier = modifier ,text = stringResource(mediaAction))
        }
    }
}