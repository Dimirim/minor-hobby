package com.dimirim.minorhobby.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.ui.login.LoginActivity
import com.dimirim.minorhobby.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("User", 0)

        if (sharedPreferences.getBoolean("logged_in", false)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

}
