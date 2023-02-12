package com.app.senseaid.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.app.senseaid.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicNavToolbar(
    @StringRes title: Int,
    @DrawableRes navigationIcon: Int,
    @StringRes navigationDescription: Int,
    modifier: Modifier,
    navigationAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(title)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
        navigationIcon = {
            Box(modifier) {
                IconButton(onClick = navigationAction) {
                    Icon(
                        painter = painterResource(navigationIcon),
                        contentDescription = stringResource(navigationDescription)
                    )
                }
            }
        }
    )
}

@Composable
fun BasicActionBar(
    @DrawableRes actionIcon: Int,
    @StringRes actionDesc: Int,
    fabAction: () -> Unit
) {
    FloatingActionButton(
        onClick = fabAction,
        shape = CircleShape,
        containerColor = Color.Cyan
    ) {
        Icon(
            painter = painterResource(id = actionIcon),
            contentDescription = stringResource(actionDesc)
        )
    }

}