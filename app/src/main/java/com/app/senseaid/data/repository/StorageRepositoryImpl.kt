package com.app.senseaid.data.repository

import com.app.senseaid.domain.repository.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {

    override fun getFileFromStorage(filePath: String): StorageReference =
        storage.getReference(filePath)

    companion object {
        private const val LOCATION_IMAGES = "locations"
    }
}