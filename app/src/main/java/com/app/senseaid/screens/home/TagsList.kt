package com.app.senseaid.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.senseaid.model.LocationTags

@Composable
fun TagsList(
    modifier: Modifier
){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xCCEEEEEE),
        shadowElevation = 4.dp
    ) {
        Column(modifier = modifier.padding(10.dp)) {
            enumValues<LocationTags>().forEach { tag ->
                Text(
                    text = tag.toString(),
                    modifier = modifier.padding(
                        vertical = 10.dp,
                        horizontal = 20.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun tagPreview() {
    TagsList(modifier = Modifier)
}