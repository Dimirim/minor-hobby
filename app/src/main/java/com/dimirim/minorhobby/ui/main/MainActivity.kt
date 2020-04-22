package com.dimirim.minorhobby.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nextIntent = Intent(this, RegisterActivity::class.java)
        startActivity(nextIntent)
    }
}
