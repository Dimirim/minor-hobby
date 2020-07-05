package com.dimirim.minorhobby.models

import androidx.lifecycle.MutableLiveData

data class ToggleTag(
    val tag: Tag,
    val isEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
)
