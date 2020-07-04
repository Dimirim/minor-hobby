package com.dimirim.minorhobby.models

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.text.SimpleDateFormat

data class Post(
    val author: String = "",
    val title: String = "",
    val content: String = "",
    val images: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val hobby: String = "",
    var likes: Int = 0,
    var views: Int = 0,
    val created: Timestamp = Timestamp.now(),
    @DocumentId val id: String = ""
) {
    @SuppressLint("SimpleDateFormat")
    fun timestampToString(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")
        return dateFormat.format(created.toDate())
    }
}
