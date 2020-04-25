package com.dimirim.minorhobby.repository.remote

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

object ImageRepository {
    private val storage = Firebase.storage.reference
    private val imageRef
        get() = storage.child("images/${System.currentTimeMillis()}.jpg")

    private suspend fun uploadImage(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        return imageRef.putBytes(data).await()
            .storage.downloadUrl.await().toString()
    }

    private suspend fun uploadImage(stream: FileInputStream): String {
        return imageRef.putStream(stream).await()
            .storage.downloadUrl.await().toString()
    }

    private suspend fun uploadImage(uri: Uri): String {
        return imageRef.putFile(uri).await()
            .storage.downloadUrl.await().toString()
    }

    private suspend fun deleteImage(fileName: String) {
        storage.child("images/$fileName").delete().await()
    }
}
