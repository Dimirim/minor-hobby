package com.dimirim.minorhobby.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.hobby_add.HobbyAddActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        lifecycleScope.launch {
            Glide.with(this@ProfileActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)
        }

        addHobbyBtn.setOnClickListener {
            startActivity(Intent(this, HobbyAddActivity::class.java))
        }
        backBtn.setOnClickListener { finish() }
    }
}
