package com.dimirim.minorhobby.ui.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.Hobby
import com.dimirim.minorhobby.models.Post
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid
    val myHobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()
    val postList: ObservableArrayList<Post> = ObservableArrayList<Post>()
    val bannerList: ArrayList<String> = ArrayList<String>()

    init {
        viewModelScope.launch {
            loadMyHobby()
            loadBanner()
        }
    }

    suspend fun loadMyHobby() {
        //HobbyRepository.getHobbyById(id?:"");

        myHobbyList.add(
            Hobby(
                "삼다수",
                "https://img3.tmon.kr/cdn2/deals/2019/03/25/1915940582/1915940582_front_e0ef78e040.jpg"
            )
        )
    }

    suspend fun loadPost() {

    }

    suspend fun loadBanner() {
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
        bannerList.add("https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/banner%2Fbanner.PNG?alt=media&token=b7fd5b24-cfb2-4b22-88a4-a50d8b504b7e")
    }
}
