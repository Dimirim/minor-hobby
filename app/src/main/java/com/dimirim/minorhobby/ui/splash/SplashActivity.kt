package com.dimirim.minorhobby.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.ui.login.LoginActivity
import com.dimirim.minorhobby.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getSharedPreferences("app_setting", Context.MODE_PRIVATE).getBoolean("registered", false)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

}
