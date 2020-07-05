package com.dimirim.minorhobby.ui.post

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.models.Tag
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import com.dimirim.minorhobby.repository.remote.TagRepository
import kotlinx.coroutines.launch

class PostViewModel(private val postId: String) : ViewModel() {

    val tagList: ObservableArrayList<Tag> = ObservableArrayList<Tag>()
    val imageList: ObservableArrayList<String> = ObservableArrayList<String>()
    val post = ObservableField<PopulatedPost>()

    init {
        viewModelScope.launch {
            post.set(getPost(postId)!!.populate())
        }
    }

    suspend fun loadTag() {
        for (tagId in getPost(postId)?.populate()!!.tags) {
            tagList.add(TagRepository.getTagById(tagId))
        }
    }

    suspend fun loadImage() {
        for (image in getPost(postId)!!.images) {
            imageList.add(image)
        }
    }

    suspend fun getPost(postId: String): Post? {
        return PostRepository.getPostById(postId)
    }
}
