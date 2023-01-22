package com.app.senseaid.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class SenseAidViewModel: ViewModel() {
    // TODO: add proper catching https://github.com/FirebaseExtended/make-it-so-android/blob/55b8504db5dc588dbe82787c4d3abb8be37a94aa/app/src/main/java/com/example/makeitso/screens/MakeItSoViewModel.kt
    fun launchCatching(snackbar: Boolean = false, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch { block }
}