package com.dimirim.minorhobby.ui.post

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityPostBinding
import com.dimirim.minorhobby.databinding.ItemImageBinding
import com.dimirim.minorhobby.databinding.ItemTagBinding
import com.dimirim.minorhobby.repository.remote.PostRepository.populate
import com.dimirim.minorhobby.ui.adapters.ImageRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.TagRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity() {

    private lateinit var viewModel: PostViewModel
    private lateinit var tagAdapter: TagRecyclerAdapter<ItemTagBinding>
    private lateinit var imageAdapter: ImageRecyclerAdapter<ItemImageBinding>
    private var postId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val binding: ActivityPostBinding =
            DataBindingUtil.setContentView<ActivityPostBinding>(this, R.layout.activity_post)

        postId = intent.getStringExtra("postId")

        viewModel =
            ViewModelProvider(this, PostViewModelFactory(postId)).get(PostViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadTag()
            viewModel.loadImage()
            viewModel.updateViews()
            if (viewModel.isLike()) {
                favorite.setImageResource(R.drawable.ic_favorite_border_24px)
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_24px)
            }
            Glide.with(this@PostActivity)
                .load(viewModel.getPost(postId)?.populate()!!.author.profile).into(profileImageView)
        }

        favorite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateLikes()
                if (viewModel.isLike()) {
                    favorite.setImageResource(R.drawable.ic_favorite_border_24px)
                } else {
                    favorite.setImageResource(R.drawable.ic_favorite_24px)
                }

                Toast.makeText(this@PostActivity, "좋아요!", Toast.LENGTH_LONG).show()

                likesTextView.text = viewModel.post.get()!!.likes.toString()
            }
        }

        tagAdapter = TagRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                }
            },
            R.layout.item_tag,
            viewModel.tagList
        )

        imageAdapter = ImageRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                }
            },
            R.layout.item_image,
            viewModel.imageList
        )

        tagRecyclerView.adapter = tagAdapter
        imageRecyclerView.adapter = imageAdapter


        backBtn.setOnClickListener {
            finish()
        }
    }

}
