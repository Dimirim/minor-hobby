package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class Tag(
    val name: String,
    @DocumentId val id: String
)
