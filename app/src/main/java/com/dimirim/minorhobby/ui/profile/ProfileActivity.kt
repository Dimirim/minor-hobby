package com.dimirim.minorhobby.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityProfileBinding
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.databinding.ItemPostLargeBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
import com.dimirim.minorhobby.ui.hobby_add.HobbyAddActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: PostRecyclerAdapter<ItemPostLargeBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadMyHobby()
            viewModel.loadMyPost()
            Glide.with(this@ProfileActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)
        }

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            },
            R.layout.item_hobby_round,
            viewModel.myHobbyList
        )

        hobbyRecyclerView.adapter = hobbyAdapter

        addHobbyBtn.setOnClickListener {
            startActivityForResult(Intent(this, HobbyAddActivity::class.java), 0)
        }
        backBtn.setOnClickListener { finish() }
    }
}
