package com.dimirim.minorhobby.ui.hobby

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.models.ToggleTag
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import com.dimirim.minorhobby.repository.remote.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HobbyViewModel : ViewModel() {
    val postList: ObservableArrayList<PopulatedPost> = ObservableArrayList()
    var filterTags: List<ToggleTag> = listOf()
    lateinit var post: PopulatedPost

    init {
        viewModelScope.launch(Dispatchers.IO) {
            filterTags = TagRepository.getAllTags().map { ToggleTag(it) }
        }
    }

    suspend fun loadPost(hobbyId: String) {
        val posts = PostRepository.getPostsByHobby(hobbyId)
        val populatedPosts = posts.map { it.populate() }
        postList.clear()
        postList.addAll(populatedPosts)
    }

    suspend fun loadPostBySearchText(hobbyId: String, searchText: String) {
        val posts = PostRepository.getPostBySearchText(hobbyId, searchText)
        val populatedPosts = posts.map { it.populate() }
        postList.clear()
        postList.addAll(populatedPosts)
    }

    suspend fun loadPostByTags(hobbyId: String, tags: List<String>, containsAll: Boolean) {
        val posts = PostRepository.getPostsByTags(hobbyId, tags, containsAll)
        val populatedPosts = posts.map { it.populate() }
        postList.clear()
        postList.addAll(populatedPosts)
    }
}
