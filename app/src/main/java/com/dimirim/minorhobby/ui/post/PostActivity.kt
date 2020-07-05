package com.dimirim.minorhobby.ui.post

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        binding.lifecycleOwner = this

        postId = intent.getStringExtra("postId")

        viewModel =
            ViewModelProvider(this, PostViewModelFactory(postId)).get(PostViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadTag()
            viewModel.loadImage()
            Glide.with(this@PostActivity)
                .load(viewModel.getPost(postId)?.populate()!!.author.profile).into(profileImageView)
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

        webView.apply {
            webChromeClient = WebChromeClient()

            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setSupportZoom(true)
            settings.defaultFixedFontSize = 40
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        tagRecyclerView.adapter = tagAdapter
        //imageRecyclerView.adapter = imageAdapter

        viewModel.post.observe( this, Observer {
            webView.loadData(it.content, "text/html","utf-8")
        })

        backBtn.setOnClickListener {
            finish()
        }
    }

}
