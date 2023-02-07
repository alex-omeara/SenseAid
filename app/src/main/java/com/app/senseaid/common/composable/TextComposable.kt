package com.app.senseaid.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    text: String,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign
    )
}

@Composable
fun SmallTextTitle(modifier: Modifier, text: String, color: Color = Color.Unspecified) {
    Text(
        text = text,
        modifier = modifier
            .padding(vertical = 5.dp),
        style = MaterialTheme.typography.titleSmall,
        color = color
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