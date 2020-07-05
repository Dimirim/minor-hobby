package com.dimirim.minorhobby.ui.hobby_add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityHobbyAddBinding
import com.dimirim.minorhobby.databinding.ItemHobbyLargeBinding
import com.dimirim.minorhobby.ui.adapters.HobbyRecyclerAdapter
import com.dimirim.minorhobby.ui.adapters.OnItemClickListener
import com.dimirim.minorhobby.ui.custom.CupertinoDialog
import kotlinx.android.synthetic.main.activity_hobby_add.*
import kotlinx.coroutines.launch

class HobbyAddActivity : AppCompatActivity() {
    private lateinit var hobbyAdapter: HobbyRecyclerAdapter<ItemHobbyLargeBinding>
    private lateinit var viewModel: HobbyAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHobbyAddBinding =
            DataBindingUtil.setContentView<ActivityHobbyAddBinding>(
                this,
                R.layout.activity_hobby_add
            )

        viewModel = ViewModelProvider(this).get(HobbyAddViewModel::class.java)
        binding.vm = viewModel

        hobbyAdapter = HobbyRecyclerAdapter(
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    lifecycleScope.launch {
                        viewModel.addHobbies(position)
                        CupertinoDialog(this@HobbyAddActivity).show("성공!", "취미가 추가되었습니다!")
                    }
                }
            },
            R.layout.item_hobby_large,
            viewModel.hobbyList
        )

        hobbyRecyclerView.adapter = hobbyAdapter
        hobbyRecyclerView.addItemDecoration(SpacesItemDecoration(this, 10))

        lifecycleScope.launch {
            viewModel.loadHobbies()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
