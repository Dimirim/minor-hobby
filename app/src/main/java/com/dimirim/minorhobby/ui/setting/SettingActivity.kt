package com.dimirim.minorhobby.ui.setting

import android.app.Activity
import android.content.Intent
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

class SettingActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private var pushAlert: Boolean = false

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

        val sps = PreferenceManager.getDefaultSharedPreferences(this)
        pushAlert = sps.getBoolean("key_push_on", false)

        backBtn.setOnClickListener { finish() }
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent()
        intent.putExtra("pushAlert", pushAlert)
        setResult(Activity.RESULT_OK, intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch {
            nameTextView.text = viewModel.getUser()!!.name
            regionTextView.text = viewModel.getUser()!!.region
            Glide.with(this@SettingActivity).load(viewModel.getUser()?.profile)
                .into(profileImageView)

            Log.d("test", pushAlert.toString())
        }
    }
}
