//package com.app.senseaid.presentation
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.app.senseaid.LocationScreen
//import com.app.senseaid.domain.model.Location
//import com.app.senseaid.ui.theme.SenseAidTheme
//
//class SelectedLocationActivity : ComponentActivity() {
//
//    private val location: Location by lazy {
//        intent.getParcelableExtra(LOCATION_ID)!!
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            SenseAidTheme {
//                LocationScreen(location = location)
//            }
//        }
//    }
//    companion object {
//        private const val LOCATION_ID = "LOCATION_ID"
//        fun newIntent(context: Context, location: Location) =
//            Intent(context, SelectedLocationActivity::class.java).apply {
//                putExtra(LOCATION_ID, location)
//            }
//    }
//}