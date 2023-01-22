package com.app.senseaid.domain.repository

import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow


interface StorageRepository {

    fun getFileFromStorage(filePath: String): StorageReference
}