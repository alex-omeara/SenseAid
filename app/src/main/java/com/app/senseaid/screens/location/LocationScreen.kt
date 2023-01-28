package com.app.senseaid.screens.location

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.R
import com.app.senseaid.screens.common.composable.TextTitle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationScreen(
    onSomethingClicked: () -> Unit,
    locationId: String,
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val location by viewModel.location
    LaunchedEffect(Unit) { viewModel.initialise(locationId) }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GlideImage(
            model = viewModel.getLocationImage(location.imgPath),
            contentDescription = location.imgDesc,
        )
        TextTitle(title = location.title)
        RatingBar(modifier, location.avgRating ?: -1.0)
    }
}

@Composable
fun RatingBar(modifier: Modifier = Modifier, averageRating: Double) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(id = R.drawable.round_star_rate_24),
            contentDescription = "star",
            tint = Color.Unspecified
        )
        Text(text = "3.6 (28)")
        Text(text = "Bright Lights")
        Text(text = "Low Sound")

    }
    
}