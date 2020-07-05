package com.dimirim.minorhobby.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.models.User
import com.dimirim.minorhobby.repository.remote.UserRepository
import com.dimirim.minorhobby.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_register_detail.*
import kotlinx.android.synthetic.main.activity_register_detail.backBtn
import kotlinx.coroutines.launch


class RegisterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_detail)

        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.places_array,
            R.layout.layout_spinner_register
        )

        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_register)

        planets_spinner.adapter = adapter

        nextBtn.setOnClickListener {
            registerUser()
        }
        backBtn.setOnClickListener { finish() }
    }

    private fun registerUser() {
        if (!checkValidation()) return

        val user = User(
            intent.getStringExtra("email")!!,
            cupertinoEditText.text,
            intent.getStringExtra("profile")!!,
            planets_spinner.selectedItem.toString()
        )

        lifecycleScope.launch {
            UserRepository.addUser(intent.getStringExtra("id")!!, user)
            val intent = Intent(this@RegisterDetailActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            getSharedPreferences("app_setting", Context.MODE_PRIVATE).edit()
                .putBoolean("registered", true).apply()
        }
    }

    private fun checkValidation() =
        if (cupertinoEditText.text.isBlank() || planets_spinner.selectedItem.toString()
                .equals("선택")
        ) {
            Toast.makeText(this, R.string.info_wrong_register, Toast.LENGTH_LONG).show()
            false
        } else if (cupertinoEditText.text.length > 10) {
            Toast.makeText(this, "사용자 이름은 10글자 이하로 작성해주세요", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
}
