package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.domain.model.Response.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLocationClicked: (String) -> Unit
) {

//    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        val locations = viewModel.locations.collectAsStateWithLifecycle(emptyList())
        LazyColumn(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            items(
                items = locations.value,
                key = { it.id }
            ) { locationItem ->
                LocationItem(
                    modifier = modifier,
                    location = locationItem,
                    onLocationClicked = onLocationClicked
                )
            }
        }
    }
}