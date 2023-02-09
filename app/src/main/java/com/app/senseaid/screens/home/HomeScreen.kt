package com.app.senseaid.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.common.popup.CenterWindowOffsetPositionProvider
import com.app.senseaid.model.Tags


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
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                    IconButton(onClick = { viewModel.onFilterPress() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                            contentDescription = stringResource(R.string.filter)
                        )
                        if (viewModel.filterState) {
                            Popup(
                                onDismissRequest = { viewModel.onFilterPress() },
                                popupPositionProvider = CenterWindowOffsetPositionProvider()
                            ) { TagsList(modifier = modifier) }
                        }
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