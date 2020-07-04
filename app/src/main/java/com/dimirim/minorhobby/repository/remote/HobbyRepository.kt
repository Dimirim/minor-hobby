package com.dimirim.minorhobby.repository.remote

import android.util.Log
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
        Log.d("testing", id)
        val hobby = hobbies.document(id).get().await()
            .toObject(Hobby::class.java) ?: return null
        hobby.population = UserRepository.getPopulationOfHobby(hobby.id)
        return hobby
    }

    suspend fun getAllHobbies(): List<Hobby> {
        val hobbies = hobbies.get().await().toObjects(Hobby::class.java)
        hobbies.map {
            it.population = UserRepository.getPopulationOfHobby(it.id)
        }
        return hobbies
    }
}
