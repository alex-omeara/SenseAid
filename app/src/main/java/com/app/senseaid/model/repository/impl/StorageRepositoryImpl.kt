package com.app.senseaid.model.repository.impl

import com.app.senseaid.model.repository.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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