package com.app.senseaid.screens.location

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.domain.model.Location
import com.app.senseaid.screens.home.HomeViewModel

@Composable
fun LocationScreen(
    onSomethingClicked: () -> Unit,
    locationId: String,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Text(text = "Hello World")
}