package com.dimirim.minorhobby.ui.hobby_add

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.dimirim.minorhobby.models.Hobby
import com.dimirim.minorhobby.models.User
import com.dimirim.minorhobby.repository.remote.HobbyRepository
import com.dimirim.minorhobby.repository.remote.UserRepository
import com.dimirim.minorhobby.repository.remote.UserRepository.update
import com.google.firebase.auth.FirebaseAuth

class HobbyAddViewModel : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val hobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()

    suspend fun loadHobbies() {
        val allHobbies = HobbyRepository.getAllHobbies()
        val myHobbies = UserRepository.getUserById(id ?: "")?.hobbies
        for (hobby in allHobbies) {
            if (!myHobbies?.contains(hobby.id)!!) hobbyList.add(hobby)
        }
    }

    suspend fun addHobbies(position: Int) {
        val user: User? = UserRepository.getUserById(id ?: "")
        val hobbies: ArrayList<String> = ArrayList()
        hobbies.addAll(user!!.hobbies)
        hobbies.add(hobbyList[position].id)
        hobbyList.removeAt(position)
        user.update("hobbies", hobbies)
    }
}
