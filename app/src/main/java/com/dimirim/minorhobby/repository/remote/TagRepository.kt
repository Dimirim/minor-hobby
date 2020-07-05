package com.dimirim.minorhobby.repository.remote

import android.util.Log
import com.dimirim.minorhobby.models.Tag
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object TagRepository {
    private val tags = Firebase.firestore.collection("tags")

    suspend fun addTag(tag: Tag): DocumentReference {
        return tags.add(tag).await()
    }

    suspend fun getTagById(id: String): Tag? {
        return tags.document(id).get().await().toObject(Tag::class.java)
    }

    suspend fun getAllTags(): List<Tag> {
        return tags.get().await().toObjects(Tag::class.java)
    }
}
