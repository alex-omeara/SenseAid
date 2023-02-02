package com.app.senseaid.screens.add_review

import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.screens.SenseAidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository
): SenseAidViewModel() {

}