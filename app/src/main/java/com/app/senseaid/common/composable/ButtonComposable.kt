package com.app.senseaid.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun IconTextButton(
    text: @Composable () -> Unit,
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    @StringRes iconDesc: Int,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier) {
        text()
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = iconDesc)
        )
    }
}

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(text = stringResource(text))
    }
}

