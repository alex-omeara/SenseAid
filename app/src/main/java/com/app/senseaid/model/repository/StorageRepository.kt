package com.app.senseaid.model.repository

import com.google.firebase.storage.StorageReference


interface StorageRepository {

    fun getFileFromStorage(filePath: String): StorageReference
}