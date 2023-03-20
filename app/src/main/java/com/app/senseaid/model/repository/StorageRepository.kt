package com.app.senseaid.model.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference


interface StorageRepository {

    fun getFileFromStorage(filePath: String): StorageReference

    fun getFileDownloadUri(filePath: String): Task<Uri>

    suspend fun addSoundFile(filePath: String): String
}