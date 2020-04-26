package com.dimirim.minorhobby.ui.profile

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.Hobby
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.models.User
import com.dimirim.minorhobby.repository.remote.HobbyRepository
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val myHobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()
    val myPostList: ObservableArrayList<Post> = ObservableArrayList<Post>()
    var user = ObservableField<User>()

    init {
        viewModelScope.launch {
            user.set(getUser())
        }
    }

    suspend fun loadMyHobby() {
        myHobbyList.clear()
        for (id in getUser()!!.hobbies) {
            myHobbyList.add(HobbyRepository.getHobbyById(id))
        }
    }

    suspend fun loadMyPost() {
        myPostList.clear()
        myPostList.addAll(PostRepository.getPostsByAuthor(id!!))
    }

    suspend fun getUser(): User? {
        return UserRepository.getUserById(id ?: "")
    }
}
