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
    }
}

//기본 취미 목록

//HobbyRepository.addHobby(Hobby(
//    "스케이트 보드",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2Fskateboard.jpg?alt=media&token=b534d477-c4c0-460e-a10e-c28d130f4173"
//))
//HobbyRepository.addHobby(Hobby(
//    "마술",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2Fmagic.jpg?alt=media&token=b94b49b3-8a83-42fc-b825-27ab71aaa636"
//))
//HobbyRepository.addHobby(Hobby(
//    "홈 인테리어",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2Fhome.jpg?alt=media&token=dbf525c1-1039-4f98-a495-2106da448a2e"
//))
//HobbyRepository.addHobby(Hobby(
//    "페인팅",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2Fpaint.jpg?alt=media&token=a4feace2-a83e-4e42-b0cf-ec9c4491ad2f"
//))
//HobbyRepository.addHobby(Hobby(
//    "식물 키우기",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2Fplant.jpg?alt=media&token=928270eb-5540-4fd4-96a2-dab9f21960e7"
//))
//HobbyRepository.addHobby(Hobby(
//    "자수",
//    "https://firebasestorage.googleapis.com/v0/b/minorhobby-40140.appspot.com/o/hobby%2FEmbroidery%20.jpg?alt=media&token=3ca1f249-d849-4317-9439-5a887e16c057"
//))
