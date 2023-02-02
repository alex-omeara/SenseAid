package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.Routes.LOCATION_ID
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.domain.model.Location
import com.app.senseaid.screens.common.composable.LocationImage
import com.app.senseaid.screens.common.composable.TextTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    location: Location,
    onLocationClicked: (String) -> Unit
) {
    Card(
        modifier = modifier.padding(6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onLocationClicked("${LOCATION_SCREEN}?${LOCATION_ID}={${location.id}}") }
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationImage(
                modifier = modifier.height(200.dp),
                imgStorageReference = viewModel.getLocationImage(location.imgPath),
                imgDesc = location.imgDesc,
            )
            TextTitle(
                title = location.title,
                textAlign = TextAlign.Center
            )
        }
    }
}