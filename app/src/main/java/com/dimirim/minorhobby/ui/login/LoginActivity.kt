package com.dimirim.minorhobby.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreferences = getSharedPreferences("User", 0)

        //if (!sharedPreferences.getBoolean("logged_in", false)) {
        //    sharedPreferences.edit().putBoolean("logged_in", true).apply()
        //}

        googleLoginBtn.setOnClickListener {
            //sharedPreferences.edit().putBoolean("logged_in", true).apply()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}
