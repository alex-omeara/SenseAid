package com.app.senseaid

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.senseaid.ui.theme.SenseAidTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun SenseAidApp() {
    SenseAidTheme {
        Surface(/*color = MaterialTheme.colorScheme.background*/) {
        val appState = rememberAppState()

        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController,coroutineScope) { SenseAidAppState(navController, coroutineScope) }

//fun Nav
