package com.app.senseaid.screens.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SmallTextTitle(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        modifier = modifier
            .padding(vertical = 5.dp),
        style = MaterialTheme.typography.titleSmall
    )
}