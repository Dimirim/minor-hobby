package com.dimirim.minorhobby.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.hobby_detail.HobbyDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var hobbyList = arrayListOf<Hobby>(
        Hobby("프랑스 자수", "", 3204),
        Hobby("비디오 게임", "", 5120),
        Hobby("드론", "", 1345),
        Hobby("아크릴 페인팅", "", 1354),
        Hobby("타로", "", 2541),
        Hobby("스케이트 보드", "", 2552)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mAdapter = MainRvAdapter(this, hobbyList) { hobby ->
            val intent = Intent(this, HobbyDetailActivity::class.java)
            intent.putExtra("hobbyName", "${hobby.name}")
            startActivity(intent)
        }
        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)
    }
}
