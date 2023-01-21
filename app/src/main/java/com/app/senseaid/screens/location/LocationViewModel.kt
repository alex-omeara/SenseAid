package com.app.senseaid.screens.location

import androidx.lifecycle.ViewModel
import com.app.senseaid.domain.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

}