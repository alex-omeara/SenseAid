package com.app.senseaid.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.app.senseaid.R
import com.app.senseaid.model.SortDirection
import com.app.senseaid.model.Tags

@Composable
fun Sort(
    modifier: Modifier,
    sortSelection: SortDirection,
    selectedTags: SnapshotStateMap<Tags, Boolean>? = null,
    excludeDirection: List<SortDirection> = emptyList(),
    onClick: (SortDirection) -> Unit
) {
    TextTitle(
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        text = stringResource(id = R.string.sort_by),
    )
    enumValues<SortDirection>().forEach { sortDirection ->
        if (!excludeDirection.contains(sortDirection)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (sortDirection == sortSelection),
                        onClick = { onClick(sortDirection) },
                        role = Role.RadioButton,
                        enabled = !(selectedTags?.containsValue(true) ?: false)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = sortDirection.toString())
                RadioButton(
                    selected = (sortDirection == sortSelection),
                    onClick = null,
                    enabled = !(selectedTags?.containsValue(true) ?: false)
                )
            }
        }
    }
}