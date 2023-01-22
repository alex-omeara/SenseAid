package com.app.senseaid.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.senseaid.Routes.LOCATION_ID
import com.app.senseaid.Routes.LOCATION_SCREEN
import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response.*
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLocationClicked: (String) -> Unit
) {

//    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        val locations = viewModel.locations.collectAsStateWithLifecycle(emptyList())
        LocationsContent(
            paddingValues = paddingValues,
            locations = locations,
            viewModel = viewModel,
            onLocationClicked = onLocationClicked
        )
    }
}

@Composable
fun LocationsContent(paddingValues: PaddingValues, locations: State<List<Location>>, viewModel: HomeViewModel, onLocationClicked: (String) -> Unit) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        items(
            items = locations.value,
//            key = { it.id }
        ) { location ->
            Log.i("LOCATION", location.id)
            LocationCard(
                location = location,
                viewModel = viewModel,
                onLocationClicked = onLocationClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationCard(location: Location, viewModel: HomeViewModel, onLocationClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onLocationClicked("$LOCATION_SCREEN?$LOCATION_ID={${location.id}}") }
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationImage(img = location.imgPath, imgDesc = location.imgDesc)
            LocationTitle(title = location.title)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationImage(img: String, imgDesc: String) {
    GlideImage(
        model = Firebase.storage.getReference(img),
        contentDescription = imgDesc,
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun LocationTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

}





//@Composable
//fun LocationProperties(location: Location) {
//    Row(
//        modifier = Modifier
//            .padding(vertical = 5.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        RatingContent(location = location)
//        Text(text = "Bright Lights")
//        Text(text = "Low Sound")
//    }
//}

//@Composable
//fun RatingContent(location: Location) {
//    Row {
//        Icon(
//            painter = painterResource(id = R.drawable.round_star_rate_24),
//            contentDescription = stringResource(id = R.string.star_rate),
//            tint = Color.Unspecified
//        )
//        Text(text = "4")
//    }
//}

//@Preview
//@Composable
//fun LocationPreview() {
//    val l = Location("Bean and Leaf", "locations/bean_and_leaf", "img description")
//    LocationScreen(location = l)
//}