package com.dimirim.minorhobby.repository.remote

import android.util.Log
import com.dimirim.minorhobby.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    private val users = Firebase.firestore.collection("users")

    suspend fun addUser(uid: String, user: User) {
        users.document(uid).set(user).await()
    }

    suspend fun getUserById(id: String): User? {
        Log.d("testing", id)
        return users.document(id).get().await().toObject(User::class.java)
    }

    suspend fun getPopulationOfHobby(hobbyId: String): Int {
        return users.whereArrayContains("hobbies", hobbyId).get().await().size()
    }

    suspend fun User.update(field: String, value: Any): User {
        users.document(this.id).update(field, value)
        return getUserById(this.id)!!
    }

    suspend fun isNameDuplicated(name: String): Boolean {
        return !users.whereEqualTo("name", name).get().await().isEmpty
    }
}
