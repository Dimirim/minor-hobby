package com.dimirim.minorhobby.ui.profile

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.Hobby
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.models.User
import com.dimirim.minorhobby.repository.remote.HobbyRepository
import com.dimirim.minorhobby.repository.remote.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val myHobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()
    val myPostList: ObservableArrayList<Post> = ObservableArrayList<Post>()
    var user: User? = User()

    init {
        viewModelScope.launch {
            loadMyHobby()
            loadMyPost()
            user = getUser()
        }
    }

    suspend fun loadMyHobby() {
        HobbyRepository.getHobbyById(id ?: "")

        myHobbyList.add(
            Hobby(
                "삼다수",
                "https://img3.tmon.kr/cdn2/deals/2019/03/25/1915940582/1915940582_front_e0ef78e040.jpg"
            )
        )
    }

    suspend fun loadMyPost() {

    }

    suspend fun getUser(): User? {
        return UserRepository.getUserById(id ?: "")
    }
}
