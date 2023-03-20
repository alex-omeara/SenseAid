package com.app.senseaid.common.composable

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharRemainingField(
    @StringRes text: Int,
    @StringRes charsRemaining: Int,
    value: String,
    modifier: Modifier,
    charsAdded: Int,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(stringResource(text)) },
        supportingText = { Text("${400 - charsAdded} ${stringResource(charsRemaining)}") },
        minLines = 7,
        maxLines = 7
    )
}