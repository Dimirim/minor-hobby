package com.dimirim.minorhobby.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.databinding.ItemPostLargeBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
import com.dimirim.minorhobby.ui.main.banner.BannerFragment
import com.dimirim.minorhobby.ui.main.banner.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: PostRecyclerAdapter<ItemPostLargeBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_hobby_round,
            viewModel.myHobbyList
        )
        hobbyRecyclerView.adapter = hobbyAdapter

        postAdapter = PostRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_post,
            viewModel.postList
        )
        postRecyclerView.adapter = postAdapter

        val fragments: ArrayList<Fragment> = ArrayList()
        for (image in viewModel.bannerList) {
            val imageUrl = Bundle().apply {
                putString("image", image)
            }
            fragments.add(BannerFragment().apply { arguments = imageUrl })
        }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments)
        bannerViewPager.adapter = viewPagerAdapter
    }
}
