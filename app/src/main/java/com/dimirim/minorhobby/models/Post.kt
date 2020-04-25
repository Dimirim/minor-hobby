package com.dimirim.minorhobby.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

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
)
