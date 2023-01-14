package com.app.senseaid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.app.senseaid.ui.theme.SenseAidTheme

class SelectedLocationActivity : ComponentActivity() {

    private val location: Location by lazy {
        intent.getParcelableExtra(LOCATION_ID)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SenseAidTheme {
                LocationScreen(location = location)
            }
        }
    }
    companion object {
        private const val LOCATION_ID = "LOCATION_ID"
        fun newIntent(context: Context, location: Location) =
            Intent(context, SelectedLocationActivity::class.java).apply {
                putExtra(LOCATION_ID, location)
            }
    }
}