package com.dimirim.minorhobby.ui.profile_edit

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.databinding.ActivityProfileEditBinding
import com.dimirim.minorhobby.repository.remote.ImageRepository
import com.dimirim.minorhobby.repository.remote.UserRepository.update
import com.dimirim.minorhobby.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_register.backBtn
import kotlinx.android.synthetic.main.activity_register.editProfileBtn
import kotlinx.android.synthetic.main.activity_register.profileImageView
import kotlinx.coroutines.launch

private const val REQUEST_IMAGE = 0

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        val binding: ActivityProfileEditBinding =
            DataBindingUtil.setContentView<ActivityProfileEditBinding>(
                this,
                R.layout.activity_profile_edit
            )

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.vm = viewModel

        lifecycleScope.launch {
            imageUrl = viewModel.getUser()!!.profile
            Glide.with(this@ProfileEditActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)
            userNameEditText.setBasicText(viewModel.getUser()!!.name)
            val number = findRegion(viewModel.getUser()!!.region)
            edit_planets_spinner.setSelection(number)
        }

        editProfileBtn.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, com.dimirim.minorhobby.ui.profile_edit.REQUEST_IMAGE)
        }

        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.places_array,
            R.layout.layout_spinner_register
        )

        adapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_register)

        edit_planets_spinner.adapter = adapter

        editSuccessBtn.setOnClickListener {
            editUser()
        }

        backBtn.setOnClickListener { finish() }
    }

    private fun editUser() {
        if (!checkValidation()) return

        Toast.makeText(this, "수정이 완료되었습니다", Toast.LENGTH_LONG).show()

        lifecycleScope.launch {
            viewModel.getUser()!!.update("profile", imageUrl)
            viewModel.getUser()!!.update("name", userNameEditText.text)
            viewModel.getUser()!!.update("region", edit_planets_spinner.selectedItem.toString())
            finish()
        }
    }

    private fun checkValidation() =
        if (userNameEditText.text.isBlank() || edit_planets_spinner.selectedItem.toString()
                .equals("선택")
        ) {
            Toast.makeText(this, R.string.info_wrong_register, Toast.LENGTH_LONG).show()
            false
        } else if (userNameEditText.text.length > 10) {
            Toast.makeText(this, "사용자 이름은 10글자 이하로 작성해주세요", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }

    private fun findRegion(region: String): Int {
        val regionArray: Array<String> =
            arrayOf(
                "선택",
                "경기도",
                "강원도",
                "충청도",
                "전라도",
                "경상도",
                "서울특별시",
                "부산광역시",
                "대구광역시",
                "인천광역시",
                "광주광역시",
                "대전광역시",
                "울산광역시",
                "세종특별자치시",
                "제주특별자치도"
            )

        return regionArray.indexOf(region)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == com.dimirim.minorhobby.ui.profile_edit.REQUEST_IMAGE && resultCode == RESULT_OK && null != data) {
            lifecycleScope.launch {
                imageUrl = ImageRepository.uploadImage(
                    contentResolver.openInputStream(data.data!!) ?: return@launch
                )
                if (imageUrl.isNotEmpty()) {
                    Glide.with(this@ProfileEditActivity).load(imageUrl).into(profileImageView)
                }
            }
        }
    }
}
