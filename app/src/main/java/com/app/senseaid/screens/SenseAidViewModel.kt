package com.app.senseaid.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.senseaid.domain.repository.StorageRepository
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class SenseAidViewModel : ViewModel() {
    @Inject
    protected lateinit var storageRepository: StorageRepository

    // TODO: add proper catching https://github.com/FirebaseExtended/make-it-so-android/blob/55b8504db5dc588dbe82787c4d3abb8be37a94aa/app/src/main/java/com/example/makeitso/screens/MakeItSoViewModel.kt
    fun launchCatching(snackbar: Boolean = false, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(block = block)

    fun getLocationImage(imgPath: String): StorageReference {
        if (imgPath == "") {
            return storageRepository.getFileFromStorage("placeholder-image.jpg")
        }
        return storageRepository.getFileFromStorage(imgPath)
    }
}