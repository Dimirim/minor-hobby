package com.dimirim.minorhobby.ui.hobby_write

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityHobbyWriteBinding
import com.dimirim.minorhobby.databinding.ItemTagBinding
import kotlinx.android.synthetic.main.activity_hobby_write.*

class HobbyWriteActivity : AppCompatActivity() {
    private lateinit var viewModel: HobbyWriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HobbyWriteViewModel::class.java)
        viewModel.hobbyId = intent.getStringExtra("hobbyId")

        val binding = DataBindingUtil
            .setContentView<ActivityHobbyWriteBinding>(this, R.layout.activity_hobby_write)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.appliedTags.observe(this, Observer {
            it.forEach { tag ->
                Log.d("HobbyWriteActivity", "Tag: $tag")
                val view = DataBindingUtil.inflate<ItemTagBinding>(
                    layoutInflater, R.layout.item_tag, tagsLayout, false)
                view.item = tag
                tagsLayout.addView(view.root)
            }
        })

        backBtn.setOnClickListener {
            finish()
        }

        richEditor.apply {
            setPlaceholder("내용을 입력하세요...")
            setPadding(16, 16, 16, 16)
        }

    }
}
