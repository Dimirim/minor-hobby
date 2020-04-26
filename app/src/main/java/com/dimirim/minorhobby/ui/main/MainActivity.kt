package com.dimirim.minorhobby.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ItemHobbyLargeBinding
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: HobbyRecyclerAdapter<ItemHobbyLargeBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_hobby_round,
            viewModel.myHobbyList
        )
        hobbyRecyclerView.adapter = hobbyAdapter

        postAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_hobby_round,
            viewModel.myHobbyList
        )
        postRecyclerView.adapter = postAdapter
    }
}
