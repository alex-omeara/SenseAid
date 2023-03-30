package com.app.senseaid.screens.home

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.model.CategoryTags
import com.app.senseaid.model.SensoryTags
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    useCategoryTag: CategoryTags? = null,
    onLocationClick: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {

    FirebaseFirestore.getInstance().clearPersistence()
    val locations =
        viewModel.getCategoryLocations(useCategoryTag, null).value.collectAsStateWithLifecycle(
            emptyList()
        )

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                useCategoryTag = useCategoryTag,
                onBackClick = onBackClick,
            )
        }
    ) { paddingValues ->

        val context = LocalContext.current
        val permissions =
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
                val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (areGranted) {
                    Log.i("AddReviewContentScreen", "permission granted")
                } else {
                    Log.i("AddReviewContentScreen", "permission denied")
                }
            }
        LaunchedEffect(Unit) {
            viewModel.getLatLong(context = context, permissions = permissions, launcher = launcher)
        }

        Column {
            LazyRow(modifier = modifier.padding(paddingValues)) {
                items(
                    items = if (useCategoryTag == null) {
                        val categoryList = CategoryTags.values().toList()
                        categoryList.drop(1)
                    } else {
                        val filteredTags: List<SensoryTags> = SensoryTags.values().filter { it.isPositive }
                        filteredTags
                    },
                    key = { it.name }
                ) { item ->
                    val color = if (viewModel.sensoryTags.contains(item) && item is SensoryTags) {
                        ButtonDefaults.filledTonalButtonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    } else {
                        ButtonDefaults.filledTonalButtonColors()
                    }
                    FilledTonalButton(
                        onClick = {
                            if (useCategoryTag == null) {
                                viewModel.onCategoryClick(item as CategoryTags, onLocationClick)
                            } else {
                                viewModel.launchCatching {
                                    viewModel.getCategoryLocations(
                                        useCategoryTag,
                                        item as SensoryTags
                                    )
                                }
                            }
                        },
                        modifier = modifier.padding(horizontal = 4.dp),
                        colors = color
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
            val listState = rememberLazyListState()
            LaunchedEffect(key1 = locations.value.firstOrNull()) { listState.scrollToItem(0) }
            LazyColumn(
                state = listState,
                modifier = modifier.padding(horizontal = 8.dp)
            ) {
                items(
                    items = locations.value,
                    key = { it.id }
                ) { locationItem ->
                    Log.i("LocationItem", "${viewModel.getLocationDistance(locationItem)} metres away from ${locationItem.title}")
                    LocationItem(
                        modifier = modifier,
                        location = locationItem,
                        onLocationPress = onLocationClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    useCategoryTag: CategoryTags?,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            if (useCategoryTag == null) {
                Text(stringResource(id = R.string.app_name))
            }
        },
        navigationIcon = {
            if (useCategoryTag != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}