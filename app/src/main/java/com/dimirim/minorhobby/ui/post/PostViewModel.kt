package com.dimirim.minorhobby.ui.post

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import kotlinx.coroutines.launch

class PostViewModel(private val postId: String) : ViewModel() {

    val post = ObservableField<PopulatedPost>()

    init {
        viewModelScope.launch {
            post.set(getPost(postId)!!.populate())
        }
    }

    suspend fun getPost(postId: String): Post? {
        return PostRepository.getPostById(postId)
    }
}
