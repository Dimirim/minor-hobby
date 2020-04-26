package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class Hobby(
    val name: String = "",
    val image: String = "",
    @DocumentId
    val id: String = ""
)
