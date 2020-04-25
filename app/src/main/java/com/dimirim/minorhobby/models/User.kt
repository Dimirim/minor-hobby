package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId

data class User(
    val email: String = "",
    val name: String = "",
    val profile: String = "",
    val region: String = "",
    @DocumentId
    val id: String = ""
)
