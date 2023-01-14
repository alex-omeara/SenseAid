package com.app.senseaid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.senseaid.ui.theme.SenseAidTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SenseAidTheme {
                val locList = remember { mutableStateListOf<Location>() }
                requestFirestoreData("locations").addOnSuccessListener { r ->
                    Log.d("Firestore", "Locations size: ${r.size()}")
                    locList.addAll(r.documents.map { loc ->
                        Location(
                            loc.get("title").toString(),
                            requestFirebaseStorageData(loc.get("imgPath").toString()).path,
                            loc.get("imgDesc").toString()
                        )
                    })
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GenerateLocationsList(locList) {
                        startActivity(SelectedLocationActivity.newIntent(this, it))
                    }
                }
            }
        }
    }

    companion object {
        fun requestFirestoreData(pathToData: String): Task<QuerySnapshot> {
            return Firebase.firestore.collection(pathToData).get()
        }

        fun requestFirebaseStorageData(pathToData: String): StorageReference {
            return Firebase.storage.reference.child(pathToData)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationCard(loc: Location, navToLocation: (Location) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

        ) {
        Column(
            modifier = Modifier.fillMaxWidth().clickable { navToLocation(loc) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("Glide", loc.img)
            GlideImage(
                model = Firebase.storage.getReference(loc.img),
                contentDescription = loc.imgDescription,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(170.dp)
                    .fillMaxWidth()
            )
            Text(
                text = loc.title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun GenerateLocationsList(locList: List<Location>, navToLocation: (Location) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)) {
        items(locList) {
            LocationCard(loc = it, navToLocation = navToLocation)
        }
    }
}