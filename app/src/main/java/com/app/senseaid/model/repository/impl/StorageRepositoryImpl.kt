package com.app.senseaid.model.repository.impl

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.app.senseaid.model.FileType
import com.app.senseaid.model.repository.StorageRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {

    override fun getFileFromStorage(filePath: String): StorageReference =
        storage.getReference(filePath)

    override fun getFileDownloadUri(filePath: String): Task<Uri> {
        return storage.getReference(filePath).downloadUrl
    }

    override suspend fun uploadFile(
        filePath: Uri,
        reviewId: String,
        fileType: FileType,
        fileName: String
    ) {
        val ref = storage.reference.child("$fileType/$reviewId-${fileName}")
        val upload = ref.putFile(filePath)
        upload.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.e(TAG, "upload of ${filePath.lastPathSegment} failed: ${it.exception}")
            } else {
                Log.i(TAG, "successfully uploaded at url: ${ref.downloadUrl}")
            }
        }
    }

    companion object {
        private const val TAG = "StorageRepository"
    }
}