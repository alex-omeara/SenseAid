package com.app.senseaid.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.R
import com.app.senseaid.common.composable.Sort
import com.app.senseaid.common.composable.TextTitle
import com.app.senseaid.model.Location
import com.app.senseaid.model.Tags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemPress: (String) -> Unit
) {
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val coroutineScope = rememberCoroutineScope()
    val locations = viewModel.locations.value.collectAsStateWithLifecycle(emptyList())

    BackdropScaffold(
        scaffoldState = scaffoldState,
        headerHeight = 24.dp,
        stickyFrontLayer = false,
        gesturesEnabled = false,
        appBar = {
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
                    IconButton(onClick = {
                        if (scaffoldState.isConcealed) {
                            coroutineScope.launch { scaffoldState.reveal() }
                            Log.i("scaffoldState", "isConcealed: ${scaffoldState.isConcealed}")
                        } else if (scaffoldState.isRevealed) {
                            coroutineScope.launch { scaffoldState.conceal() }
                            Log.i("scaffoldState", "isConcealed: ${scaffoldState.isConcealed}")
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24), // TODO: change icon
                            contentDescription = stringResource(R.string.filter)
                        )
                    }
                }
            )
        },
        backLayerContent = {
            BackLayerContent(modifier = modifier, onItemPress = onItemPress, locations = locations)
        },
        frontLayerContent = {
            FrontLayerContent(
                modifier = modifier,
                coroutineScope = coroutineScope,
                scaffoldState = scaffoldState,
            )
        }) {

    }
}

@Composable
fun BackLayerContent(
    modifier: Modifier,
    locations: State<List<Location>>,
    onItemPress: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = locations.value.firstOrNull()) { listState.scrollToItem(0) }
    LazyColumn(
        modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
        state = listState
    ) {
        items(
            items = locations.value,
            key = { it.id }
        ) { locationItem ->
            LocationItem(
                modifier = modifier,
                location = locationItem,
                onLocationPress = onItemPress
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FrontLayerContent(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    scaffoldState: BackdropScaffoldState,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Column {
        Row(
            modifier = modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    scaffoldState.reveal()
                    if (viewModel.selectedTags.containsValue(true)) {
                        viewModel.filterLocations()
                    } else {
                        viewModel.sortLocations()
                    }
                }

                Log.i("scaffoldState", "isConcealed: ${scaffoldState.isConcealed}")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = stringResource(R.string.cancel)
                )
            }
            TextTitle(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.filter_and_sort),
                textAlign = TextAlign.Center
            )
        }
        Divider()
        Sort(
            modifier = modifier,
            sortSelection = viewModel.sortSelection,
            onClick = viewModel::onSortByPress,
            selectedTags = viewModel.selectedTags
        )
        Divider()
        Filter(
            modifier = modifier,
            selectedTags = viewModel.selectedTags,
            onTagSelect = viewModel::onTagSelect
        ) { viewModel.resetSort() }
    }
}

@Composable
fun Filter(
    modifier: Modifier,
    selectedTags: SnapshotStateMap<Tags, Boolean>,
    onTagSelect: (Tags) -> Unit,
    resetSort: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextTitle(
            modifier = modifier.padding(vertical = 12.dp),
            text = stringResource(id = R.string.filter),
        )
        if (selectedTags.containsValue(true)) {
            TextButton(onClick = {
                selectedTags.keys.forEach { selectedTags[it] = false }
            }) {
                Text(text = stringResource(id = R.string.clear))
            }
        }
    }
    enumValues<Tags>().forEach { tag ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .selectable(
                    selected = (selectedTags[tag] ?: false),
                    onClick = {
                        onTagSelect(tag)
                        resetSort()
                    },
                    role = Role.Checkbox
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = tag.toString())
            Checkbox(
                checked = (selectedTags[tag] ?: false),
                onCheckedChange = null
            )
        }
    }
}
