package com.dimirim.minorhobby.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class Hobby(
    val name: String = "",
    val image: String = "",
    @DocumentId val id: String = "",
    @Exclude var population: Int = 0
)
