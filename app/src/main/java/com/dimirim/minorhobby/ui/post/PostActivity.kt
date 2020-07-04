package com.dimirim.minorhobby.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.R

class PostActivity : AppCompatActivity() {

    private var postId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        postId = intent.getStringExtra("postId")
    }
}
