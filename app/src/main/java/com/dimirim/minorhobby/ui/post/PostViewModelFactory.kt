package com.dimirim.minorhobby.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostViewModelFactory(private val postId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            PostViewModel(postId) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
