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

class RegisterActivity : AppCompatActivity() {
    private val REQUEST_IMAGE = 0
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editProfileBtn.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, REQUEST_IMAGE)
        }

        backBtn.setOnClickListener { finish() }
        skipBtn.setOnClickListener {
            startActivity(Intent(this, RegisterDetailActivity::class.java))
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
