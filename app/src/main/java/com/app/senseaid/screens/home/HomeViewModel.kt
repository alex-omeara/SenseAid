package com.app.senseaid.screens.home

import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository
) : SenseAidViewModel() {

    val locations = firestoreRepository.locations

//    fun getLocationImage(imgPath: String) = storageRepository.getFileFromStorage(imgPath)
}