package com.dimirim.minorhobby.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private var pushAlert by Delegates.notNull<Boolean>()
    private lateinit var sps: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        lifecycleScope.launch {
            nameTextView.text = viewModel.getUser()!!.name
            regionTextView.text = viewModel.getUser()!!.region
            Glide.with(this@SettingActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)
        }

        sps = PreferenceManager.getDefaultSharedPreferences(this)

        backBtn.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            nameTextView.text = viewModel.getUser()!!.name
            regionTextView.text = viewModel.getUser()!!.region
            Glide.with(this@SettingActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)

            pushAlert = sps.getBoolean("key_push_on", false)

            Log.d("test", pushAlert.toString())
        }
    }
}
