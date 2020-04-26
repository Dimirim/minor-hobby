package com.dimirim.minorhobby.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_detail)

        backBtn.setOnClickListener { finish() }
    }
}
