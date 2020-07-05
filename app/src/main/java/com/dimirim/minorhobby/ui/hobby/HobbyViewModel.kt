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
    var allTags: List<ToggleTag> = listOf()
    var containsAll = false
    lateinit var post: PopulatedPost

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allTags = TagRepository.getAllTags().map { ToggleTag(it) }
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
        var populatedPosts = posts
            .map { it.populate() }
        val filterTags = allTags.filter { it.isEnabled.value!! }.map { it.tag.id }
        if (filterTags.isNotEmpty()) {
            populatedPosts = posts
                .map { it.populate() }
                .filter { post ->
                    if (containsAll) post.tags.containsAll(filterTags)
                    else {
                        filterTags.forEach {
                            if (post.tags.contains(it)) return@filter true
                        }
                        false
                    }
                }
        }
        postList.clear()
        postList.addAll(populatedPosts)
    }

    fun updateFilter(containsAll: Boolean) {
        this.containsAll = containsAll
    }
}
