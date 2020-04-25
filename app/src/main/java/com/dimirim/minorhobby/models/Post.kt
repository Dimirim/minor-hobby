package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class Post(
    val author: String = "",
    val title: String = "",
    val content: String = "",
    val images: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val hobby: String = "",
    @DocumentId val id: String = ""
)
