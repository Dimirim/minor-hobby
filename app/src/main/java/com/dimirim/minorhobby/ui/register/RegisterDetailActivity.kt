package com.dimirim.minorhobby.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_register_detail.*

class RegisterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_detail)

        nextBtn.setOnClickListener {
            startActivity(Intent(this@RegisterDetailActivity, MainActivity::class.java))
        }
        backBtn.setOnClickListener { finish() }
    }
}
