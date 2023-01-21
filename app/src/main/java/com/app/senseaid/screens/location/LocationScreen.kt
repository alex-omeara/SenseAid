package com.app.senseaid.screens.location

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.senseaid.screens.home.HomeViewModel

@Composable
fun LocationScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Text(text = "Hello World")
}