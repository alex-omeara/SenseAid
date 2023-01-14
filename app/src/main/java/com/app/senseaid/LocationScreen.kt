package com.app.senseaid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun LocationScreen(location: Location) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    LocationHeader(
                        location = location,
                        containerHeight = this@BoxWithConstraints.maxHeight
                    )
                    LocationContent(
                        location = location,
                        containerHeight = this@BoxWithConstraints.maxHeight
                    )
                    LocationProperties(location = location)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationHeader(location: Location, containerHeight: Dp) {
    GlideImage(
        model = Firebase.storage.getReference(location.img),
        contentDescription = location.imgDescription,
        modifier = Modifier
            .heightIn(max = containerHeight / 4)
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun LocationContent(location: Location, containerHeight: Dp) {
    Column {
        Title(location = location)
    }
}

@Composable
fun Title(location: Location) {
    Text(
        text = location.title,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LocationProperties(location: Location) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RatingContent(location = location)
        Text(text = "Bright Lights")
        Text(text = "Low Sound")
    }
}

@Composable
fun RatingContent(location: Location) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.round_star_rate_24),
            contentDescription = stringResource(id = R.string.star_rate),
            tint = Color.Unspecified
        )
        Text(text = "4")
    }
}

@Preview
@Composable
fun LocationPreview() {
    val l = Location("Bean and Leaf", "locations/bean_and_leaf", "img description")
    LocationScreen(location = l)
}