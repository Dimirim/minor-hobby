package com.dimirim.minorhobby.ui.hobby_write

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.models.Post
import com.dimirim.minorhobby.models.Tag
import com.dimirim.minorhobby.repository.remote.PostRepository
import com.dimirim.minorhobby.repository.remote.TagRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HobbyWriteViewModel : ViewModel() {
    var hobbyId = ""
    val title = MutableLiveData("")
    val content = MutableLiveData("")

    val appliedTags = ArrayList<Tag>()
    val images = ArrayList<String>()
    val selectAbleTags = MutableLiveData(listOf<Tag>())

    init {
        viewModelScope.launch {
            selectAbleTags.value = TagRepository.getAllTags()
        }
    }

    fun onCompleteButtonClick(view: View) = viewModelScope.launch {
        if (!isValid()) return@launch
        val post = Post(
            FirebaseAuth.getInstance().currentUser!!.uid,
            title.value!!,
            content.value!!,
            images,
            appliedTags.map { it.name },
            hobbyId
        )

        PostRepository.addPost(post)
        Toast.makeText(view.context, "게시물이 작성 되었습니다", Toast.LENGTH_LONG).show()
    }

    fun onTagsClick(view: View) {
        AlertDialog.Builder(view.context)
            .setView(R.layout.layout_tags)
            .setTitle(R.string.tag)
            .setPositiveButton(R.string.apply) { _, _ ->

            }
    }

    private fun isValid() = title.value!!.isNotBlank() && content.value!!.isNotBlank()
}
