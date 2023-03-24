package com.app.senseaid.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.BackdropScaffold
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.rememberBackdropScaffoldState
//import androidx.compose.material.BackdropScaffoldState
//import androidx.compose.material.BackdropValue
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.model.LocationTags
import com.app.senseaid.model.CategoryTags
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
            if (!viewModel.isSearching) {
                DefaultTopAppBar(
                    useCategoryTag = useCategoryTag,
                    onBackClick = onBackClick,
                    onSearchClick = { viewModel.updateIsSearching() }
                )
            } else {
                SearchAppBar(
                    text = viewModel.searchText,
                    useCategoryTag = useCategoryTag,
                    onTextChange = viewModel::updateSearchText,
                    onSearchClick =  viewModel::onSearchClick,
                    onCloseClick = { viewModel.updateIsSearching() }
                )
            }
        }
    ) { paddingValues ->

        Column() {
            LazyRow(modifier = modifier.padding(paddingValues)) {
                items(
                    items = if (useCategoryTag == null) {
                        val array = CategoryTags.values()
                        array.sliceArray(IntRange(1, array.size - 1))
                    } else {
                        LocationTags.values()
                    },
                    key = { it.name }
                ) { item ->
                    val color = if (viewModel.locationTags.contains(item) && item is LocationTags) {
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
                                        item as LocationTags
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    useCategoryTag: CategoryTags?,
    onTextChange: (String) -> Unit,
    onSearchClick: (String, CategoryTags?) -> Unit,
    onCloseClick: () -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(id = R.string.searchIcon)
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClick()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClick(text, useCategoryTag) }),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    useCategoryTag: CategoryTags?,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
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