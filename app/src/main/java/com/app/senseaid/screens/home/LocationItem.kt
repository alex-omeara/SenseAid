package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.common.composable.RatingText
import com.app.senseaid.model.Location
import com.app.senseaid.common.composable.TextTitle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    location: Location,
    onLocationPress: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        onClick = { viewModel.onLocationClick(location.id, onLocationPress) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(6.dp)),
                model = viewModel.getLocationImage(location.imgPath),
                contentDescription = location.imgDesc,
                contentScale = ContentScale.FillWidth
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingText(
                    modifier = modifier.weight(1f),
                    averageRating = location.avgRating,
                    totalReviews = null
                )
                Spacer(modifier = Modifier.weight(1f))
                TextTitle(
                    modifier = modifier.weight(3f),
                    text = location.title,
                )
            }
        }
    }
}