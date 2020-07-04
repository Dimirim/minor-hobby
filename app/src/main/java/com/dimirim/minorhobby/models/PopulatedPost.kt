package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class PopulatedPost(
    val author: User,
    val title: String,
    val content: String,
    val images: List<String>,
    val tags: List<String>,
    val hobby: String,
    var likes: Int,
    var views: Int,
    val created: String,
    @DocumentId val id: String
)
