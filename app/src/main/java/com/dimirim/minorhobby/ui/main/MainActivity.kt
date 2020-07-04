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
import com.dimirim.minorhobby.databinding.ItemPostBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
import com.dimirim.minorhobby.ui.hobby.HobbyActivity
import com.dimirim.minorhobby.ui.main.banner.BannerFragment
import com.dimirim.minorhobby.ui.main.banner.ViewPagerAdapter
import com.dimirim.minorhobby.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: PostRecyclerAdapter<ItemPostBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
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

            viewModel.loadMyHobby()
        }

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    var intent = Intent(this@MainActivity, HobbyActivity::class.java)
                    intent.putExtra("hobbyName", viewModel.myHobbyList[position].name)
                    intent.putExtra("hobbyId", viewModel.myHobbyList[position].id)
                    startActivityForResult(intent, 0)
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
            startActivityForResult(Intent(this, ProfileActivity::class.java), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            Glide.with(this@MainActivity).load(viewModel.getUser()?.profile).into(profileImageView)
            viewModel.loadPost()
            viewModel.loadMyHobby()
        }
    }
}
