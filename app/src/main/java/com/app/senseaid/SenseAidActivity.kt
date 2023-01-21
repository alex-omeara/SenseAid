package com.app.senseaid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.senseaid.ui.theme.SenseAidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SenseAidActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SenseAidApp() }
    }
}