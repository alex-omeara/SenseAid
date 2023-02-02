package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.domain.model.Response.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLocationPress: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = R.string.back_button.toString()
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val locations = viewModel.locations.collectAsStateWithLifecycle(emptyList())

        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
        ) {
            items(
                items = locations.value,
                key = { it.id }
            ) { locationItem ->
                LocationItem(
                    modifier = modifier,
                    location = locationItem,
                    onLocationPress = onLocationPress
                )
            }
        }
    }
}