package com.app.senseaid.common.composable

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun BasicTextButton(
    @StringRes text: Int,
    modifier: Modifier,
    action: () -> Unit
) {
    TextButton(onClick = action, modifier) {
        Text(text = stringResource(text))
    }
}

@Composable
fun IconTextButton(
    text: String,
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    @StringRes iconDesc: Int,
    action: () -> Unit
) {
    TextButton(onClick = action, modifier) {
        Text(text = text)
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
    action: () -> Unit
) {
   Button(
       onClick = action,
       modifier = modifier,
       colors = ButtonDefaults.buttonColors(
           containerColor = MaterialTheme.colorScheme.primary,
           contentColor = MaterialTheme.colorScheme.onPrimary
       ),
       enabled = enabled
   ) {
       Text(text = stringResource(text))
   }
}

