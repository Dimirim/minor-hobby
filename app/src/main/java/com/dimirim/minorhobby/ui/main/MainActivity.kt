package com.dimirim.minorhobby.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.databinding.ItemPostLargeBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
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
    }
}
