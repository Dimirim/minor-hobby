package com.dimirim.minorhobby.ui.hobby_write

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        viewModel.selectAbleTags.observe(this, Observer {
            it.forEach { tag ->
                Log.d("HobbyWriteActivity", "Tag: $tag")
                val view = DataBindingUtil.inflate<ItemTagBinding>(
                    layoutInflater, R.layout.item_tag, tagsLayout, false
                )
                view.item = tag
                view.root.setOnClickListener {
                    if (viewModel.appliedTags.contains(tag)) {
                        Log.d("TagItem", "removed!")
                        it.background = this.getDrawable(R.drawable.shape_round)
                        (it as TextView).setTextColor(getColor(R.color.colorAccent))
                        viewModel.appliedTags.remove(tag)
                    } else {
                        Log.d("TagItem", "added!")
                        it.background = this.getDrawable(R.drawable.shape_round_accent)
                        (it as TextView).setTextColor(getColor(R.color.colorPrimary))
                        viewModel.appliedTags.add(tag)
                    }
                }
                tagsLayout.addView(view.root)
            }
        })

        backBtn.setOnClickListener {
            finish()
        }

        richEditor.apply {
            setPlaceholder("내용을 입력하세요...")
            setPadding(16, 16, 16, 16)
            setOnTextChangeListener {
                viewModel.content.value = it
            }
        }

        //Editor 버튼 설정
        boldBtn.setOnClickListener { richEditor.setBold() }
        italicBtn.setOnClickListener { richEditor.setItalic() }
        h1Btn.setOnClickListener { richEditor.setHeading(1) }
        h2Btn.setOnClickListener { richEditor.setHeading(2) }
        h3Btn.setOnClickListener { richEditor.setHeading(3) }
        undoBtn.setOnClickListener { richEditor.undo() }
        cameraBtn.setOnClickListener {
            //val url = viewModel.
            //richEditor.insertImage("")
        }
    }
}
