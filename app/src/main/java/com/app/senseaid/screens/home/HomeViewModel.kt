package com.app.senseaid.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.domain.repository.StorageRepository
import com.app.senseaid.screens.SenseAidViewModel
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository,
    val storageRepository: StorageRepository
    ) : SenseAidViewModel() {

    val locations = firestoreRepository.locations

    fun getLocationImage(imgPath: String) = storageRepository.getFileFromStorage(imgPath)
}