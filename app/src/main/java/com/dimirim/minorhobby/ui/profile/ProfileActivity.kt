package com.dimirim.minorhobby.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityProfileBinding
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.databinding.ItemPostBinding
import com.dimirim.minorhobby.repository.remote.UserRepository.update
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.adapters.PostRecyclerAdapter
import com.dimirim.minorhobby.ui.hobby_add.HobbyAddActivity
import com.dimirim.minorhobby.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyRoundBinding>
    private lateinit var postAdapter: PostRecyclerAdapter<ItemPostBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadMyPost()
            Glide.with(this@ProfileActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)
            viewModel.loadMyHobby()
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
            viewModel.myPostList
        )

        hobbyRecyclerView.adapter = hobbyAdapter
        postRecyclerView.adapter = postAdapter

        addHobbyBtn.setOnClickListener {
            startActivityForResult(Intent(this, HobbyAddActivity::class.java), 0)
        }
        backBtn.setOnClickListener { finish() }

        settingBtn.setOnClickListener {
            startActivityForResult(Intent(this@ProfileActivity, SettingActivity::class.java), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            viewModel.loadMyHobby()
            viewModel.loadMyPost()
            if (resultCode == Activity.RESULT_OK) {
                val pushAlert: Boolean = data!!.getBooleanExtra("pushAlert", false)
                Log.d("test", pushAlert.toString())
                viewModel.getUser()!!.update("pushAlert", pushAlert)
            }
        }
    }
}
