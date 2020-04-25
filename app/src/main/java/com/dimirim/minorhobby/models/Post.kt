package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class Post(
    val author: String = "",
    val title: String = "",
    val content: String = "",
    val image: List<String> = listOf(),
    val category: String = "",
    val hobby: String = "",
    @DocumentId val id: String = ""
)
