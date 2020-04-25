package com.dimirim.minorhobby.repository.remote

import com.dimirim.minorhobby.models.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    private val users = Firebase.firestore.collection("users")

    suspend fun addUser(user: User): DocumentReference {
        return users.add(user).await()
    }

    suspend fun getUserById(id: String): User? {
        return users.document(id).get().await().toObject(User::class.java)
    }
}
