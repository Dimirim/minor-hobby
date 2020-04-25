package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class Hobby(
    @DocumentId
    val id: String = "",
    val name: String = ""
)
