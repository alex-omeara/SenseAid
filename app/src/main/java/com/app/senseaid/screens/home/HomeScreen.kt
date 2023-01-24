package com.app.senseaid.screens.home

import android.annotation.SuppressLint
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
import com.app.senseaid.screens.SenseAidViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLocationClicked: (String) -> Unit
) {

//    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        val locations = viewModel.locations.collectAsStateWithLifecycle(emptyList())

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            items(
                items = locations.value,
                key = { it.id }
            ) { locationItem ->
                LocationItem(
                    location = locationItem,
                    onLocationClicked = onLocationClicked
                )
            }
        }
    }
}