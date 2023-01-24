package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.Routes
import com.app.senseaid.domain.model.Location
import com.app.senseaid.screens.common.composable.TextTitle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    location: Location,
    onLocationClicked: (String) -> Unit
) {
    Card(
        modifier = modifier.padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onLocationClicked("${Routes.LOCATION_SCREEN}?${Routes.LOCATION_ID}={${location.id}}") }
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = viewModel.getLocationImage(location.imgPath),
                contentDescription = location.imgDesc,
                modifier = modifier,
                contentScale = ContentScale.FillWidth
            )
            TextTitle(title = location.title)
        }
    }
}