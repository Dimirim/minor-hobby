package com.dimirim.minorhobby.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityPostBinding
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity() {

    private lateinit var viewModel: PostViewModel
    private var postId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val binding: ActivityPostBinding =
            DataBindingUtil.setContentView<ActivityPostBinding>(this, R.layout.activity_post)

        postId = intent.getStringExtra("postId")

        viewModel =
            ViewModelProvider(this, PostViewModelFactory(postId)).get(PostViewModel::class.java)

        lifecycleScope.launch {
            Glide.with(this@PostActivity)
                .load(viewModel.getPost(postId)?.populate()!!.author.profile).into(profileImageView)
        }

        binding.vm = viewModel

        backBtn.setOnClickListener {
            finish()
        }
    }

}
