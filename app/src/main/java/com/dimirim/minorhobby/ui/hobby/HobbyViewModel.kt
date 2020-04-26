package com.dimirim.minorhobby.ui.hobby

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.repository.remote.PostRepository

class HobbyViewModel : ViewModel() {
    val postList: ObservableArrayList<Post> = ObservableArrayList<Post>()

    suspend fun loadPost(hobbyId: String) {
        postList.clear()
        postList.addAll(PostRepository.getPostsByHobby(hobbyId))
    }
}
