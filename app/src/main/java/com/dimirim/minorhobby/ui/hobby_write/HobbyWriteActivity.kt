package com.dimirim.minorhobby.ui.hobby_write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityHobbyWriteBinding
import com.dimirim.minorhobby.databinding.ItemTagBinding
import com.dimirim.minorhobby.repository.remote.ImageRepository
import kotlinx.android.synthetic.main.activity_hobby_write.*
import kotlinx.android.synthetic.main.activity_hobby_write.backBtn
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch

private const val REQUEST_IMAGE = 0

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
            setEditorFontSize(40)
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.builtInZoomControls = true
            settings.setSupportZoom(true)
        }

        //Editor 버튼 설정
        boldBtn.setOnClickListener { richEditor.setBold() }
        italicBtn.setOnClickListener { richEditor.setItalic() }
        h1Btn.setOnClickListener { richEditor.setHeading(1) }
        h2Btn.setOnClickListener { richEditor.setHeading(2) }
        h3Btn.setOnClickListener { richEditor.setHeading(3) }
        undoBtn.setOnClickListener { richEditor.undo() }
        cameraBtn.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && null != data) {
            lifecycleScope.launch {
                val imageUrl = ImageRepository.uploadImage(
                    contentResolver.openInputStream(data.data!!) ?: return@launch
                )
                if (imageUrl.isNotEmpty()) {
                    richEditor.insertImage(imageUrl, "image")
                    viewModel.images.value!!.add(imageUrl)
                }
            }
        }
    }
}
