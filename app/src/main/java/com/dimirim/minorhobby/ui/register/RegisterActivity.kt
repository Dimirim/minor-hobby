package com.dimirim.minorhobby.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.repository.remote.ImageRepository
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch

private const val REQUEST_IMAGE = 0

class RegisterActivity : AppCompatActivity() {
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageUrl = intent.getStringExtra("profile") ?: ""
        if (imageUrl.isNotBlank()) Glide.with(this).load(imageUrl).into(profileImageView)

        editProfileBtn.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, REQUEST_IMAGE)
        }

        backBtn.setOnClickListener { finish() }
        skipBtn.setOnClickListener {
            val intent = Intent(this, RegisterDetailActivity::class.java).apply {
                putExtra("id", intent.getStringExtra("id"))
                putExtra("email", intent.getStringExtra("email"))
                putExtra("profile", imageUrl)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && null != data) {
            lifecycleScope.launch {
                imageUrl = ImageRepository.uploadImage(
                    contentResolver.openInputStream(data.data!!) ?: return@launch
                )

                if (imageUrl.isNotEmpty()) {
                    nextTextView.text = "다음으로"
                    Glide.with(this@RegisterActivity).load(imageUrl).into(profileImageView)
                }
            }
        }
    }

}
