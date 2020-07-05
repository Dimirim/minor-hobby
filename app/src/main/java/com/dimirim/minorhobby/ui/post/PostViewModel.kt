package com.dimirim.minorhobby.ui.post

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.models.PopulatedPost
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.models.Tag
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import com.dimirim.minorhobby.repository.remote.PostRepository.update
import com.dimirim.minorhobby.repository.remote.TagRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class PostViewModel(private val postId: String) : ViewModel() {
    val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val tagList: ObservableArrayList<Tag> = ObservableArrayList<Tag>()
    val imageList: ObservableArrayList<String> = ObservableArrayList<String>()
    val post = MutableLiveData<PopulatedPost>()

    init {
        viewModelScope.launch {
            post.value = (getPost(postId)!!.populate())
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

    suspend fun isLike(): Boolean {
        post.set(getPost(postId)!!.populate())

        var chk = true

        for (user in post.get()!!.likeUsers) {
            if (user == id) {
                chk = false
            }
        }

        post.set(getPost(postId)!!.populate())

        return chk
    }

    suspend fun updateLikes() {
        post.set(getPost(postId)!!.populate())

        var likes = post.get()!!.likes

        val updateView: MutableList<String> = post.get()!!.viewUsers.toMutableList()

        if (!isLike()) {
            getPost(postId)!!.update("likes", --likes)

            updateView.remove(id)
            Log.d("removeTest", updateView.toList().toString())
            getPost(postId)!!.update("likeUsers", updateView.toList())

            post.set(getPost(postId)!!.populate())
        } else {
            getPost(postId)!!.update("likes", ++likes)

            updateView.add(id)
            getPost(postId)!!.update("likeUsers", updateView.toList())

            post.set(getPost(postId)!!.populate())
        }
    }

    suspend fun updateViews() {
        post.set(getPost(postId)!!.populate())

        var views = post.get()!!.views
        getPost(postId)!!.update("views", ++views)

        var chk = true
        for (user in post.get()!!.viewUsers) {
            if (user == id) {
                chk = false
                return
            }
        }
        if (chk) {
            val updateView: MutableList<String> = post.get()!!.viewUsers.toMutableList()
            updateView.add(id)
            getPost(postId)!!.update("viewUsers", updateView.toList())
        }
    }

    suspend fun getPost(postId: String): Post? {
        return PostRepository.getPostById(postId)
    }
}
