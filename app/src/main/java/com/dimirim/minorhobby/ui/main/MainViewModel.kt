package com.dimirim.minorhobby.ui.main

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

class MainViewModel : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val myHobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()
    val postList: ObservableArrayList<Post> = ObservableArrayList<Post>()
    val bannerList: ArrayList<String> = ArrayList<String>()
    val user = ObservableField<User>()

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

    suspend fun loadPost() {
        postList.clear()
        postList.addAll(PostRepository.getAllPosts())
    }

    suspend fun loadBanner(): ArrayList<String> {
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
        return bannerList
    }

    suspend fun getUser(): User? {
        return UserRepository.getUserById(id ?: "")
    }
}
