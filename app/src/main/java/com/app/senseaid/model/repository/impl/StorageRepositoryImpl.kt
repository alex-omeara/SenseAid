package com.app.senseaid.model.repository.impl

import android.net.Uri
import android.util.Log
import com.app.senseaid.model.repository.StorageRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {

    override fun getFileFromStorage(filePath: String): StorageReference =
        storage.getReference(filePath)

    override fun getFileDownloadUri(filePath: String): Task<Uri> {
        return storage.getReference(filePath).downloadUrl
    }

    override suspend fun addSoundFile(filePath: String): String {
        val file = Uri.fromFile(File(filePath))
        val ref = storage.reference.child("audio/${file.lastPathSegment}")
        val upload = ref.putFile(file)
        upload.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.e(TAG, "upload of ${file.lastPathSegment} failed: ${it.exception}")
            } else {
                Log.i(TAG, "successfully uploaded at url: ${ref.downloadUrl}")
            }
        }
        return ref.path
    }

    companion object {
        private const val TAG = "StorageRepository"
    }
}