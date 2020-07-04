package com.dimirim.minorhobby.ui.hobby

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.PostRepository.populate

class HobbyViewModel : ViewModel() {
    val postList: ObservableArrayList<PopulatedPost> = ObservableArrayList()

    suspend fun loadPost(hobbyId: String) {
        val posts = PostRepository.getPostsByHobby(hobbyId)
        val populatedPosts = posts.map { it.populate() }
        postList.clear()
        postList.addAll(populatedPosts)
    }

    suspend fun loadPostBySearchText(hobbyId: String, searchText: String) {
        postList.clear()
        postList.addAll(PostRepository.getPostBySearchText(hobbyId, searchText))
    }

    suspend fun loadPostByTags(hobbyId: String, tags: List<String>, containsAll: Boolean) {
        postList.clear()
        postList.addAll(PostRepository.getPostsByTags(hobbyId, tags, containsAll))
    }
}
