package com.dimirim.minorhobby.ui.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.Hobby
import com.dimirim.minorhobby.models.Post
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val myHobbyList: ObservableArrayList<Hobby> = ObservableArrayList<Hobby>()
    val postList: ObservableArrayList<Post> = ObservableArrayList<Post>()

    init {
        viewModelScope.launch {
            loadMyHobby()
        }
    }

    suspend fun loadMyHobby() {
        //HobbyRepository.getHobbyById()
        //myHobbyList.clear()
        myHobbyList.add(
            Hobby(
                "삼다수",
                "https://img3.tmon.kr/cdn2/deals/2019/03/25/1915940582/1915940582_front_e0ef78e040.jpg"
            )
        )
    }
}
