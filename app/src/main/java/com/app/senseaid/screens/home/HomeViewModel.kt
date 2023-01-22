package com.app.senseaid.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.senseaid.domain.model.Location
import com.app.senseaid.domain.model.Response.Loading
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.domain.repository.LocationsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {
    var locationsResponse by mutableStateOf<LocationsResponse>(Loading)
        private set

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            repository.getLocationsFromFirestore().collect {
                locationsResponse = it
            }
        }
    }
}