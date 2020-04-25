package com.dimirim.minorhobby.repository.remote

import com.dimirim.minorhobby.models.Hobby
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object HobbyRepository {
    private val hobbies = Firebase.firestore.collection("hobbies")

    suspend fun addHobby(hobby: Hobby): DocumentReference {
        return hobbies.add(hobby).await()
    }

    suspend fun getHobbyById(id: String): Hobby? {
        return hobbies.document(id).get().await().toObject(Hobby::class.java)
    }

    suspend fun getAllHobbies(): List<Hobby> {
        return hobbies.get().await().toObjects(Hobby::class.java)
    }
}
