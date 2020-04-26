package com.dimirim.minorhobby.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityMainBinding
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.databinding.ItemPostLargeBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
import com.dimirim.minorhobby.ui.main.banner.BannerFragment
import com.dimirim.minorhobby.ui.main.banner.ViewPagerAdapter
import com.dimirim.minorhobby.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: PostRecyclerAdapter<ItemPostLargeBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadMyHobby()
            viewModel.loadPost()

            Glide.with(this@MainActivity).load(viewModel.getUser()?.profile).into(profileImageView)

            val fragments: ArrayList<Fragment> = ArrayList()
            for (image in viewModel.loadBanner()) {
                val imageUrl = Bundle().apply {
                    putString("image", image)
                }
                fragments.add(BannerFragment().apply { arguments = imageUrl })
            }
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments)
            bannerViewPager.adapter = viewPagerAdapter
        }

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_hobby_round,
            viewModel.myHobbyList
        )

        postAdapter = PostRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_post,
            viewModel.postList
        )

        hobbyRecyclerView.adapter = hobbyAdapter
        postRecyclerView.adapter = postAdapter

        profileImageView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
