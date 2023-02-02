package com.app.senseaid.screens.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.storage.StorageReference

@Composable
fun TextTitle(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = title,
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationImage(
    modifier: Modifier = Modifier,
    imgStorageReference: StorageReference,
    imgDesc: String,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    GlideImage(
        modifier = modifier,
        model = imgStorageReference,
        contentDescription = imgDesc,
        contentScale = contentScale
    )
}