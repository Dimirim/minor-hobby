package com.dimirim.minorhobby.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.repository.remote.UserRepository.update
import com.dimirim.minorhobby.ui.profile.ProfileViewModel
import com.dimirim.minorhobby.ui.profile_edit.ProfileEditActivity
import kotlinx.coroutines.launch


class MainPreference : PreferenceFragmentCompat() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val switchPref = findPreference("key_push_on") as SwitchPreference?

        lifecycleScope.launch {
            switchPref!!.isChecked = viewModel.getUser()!!.pushAlert
        }

        switchPref!!.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                lifecycleScope.launch {
                    viewModel.getUser()!!.update("pushAlert", newValue)
                }
                true
            }

        val myPref =
            findPreference("key_profile_edit") as Preference?

        myPref!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { //open browser or intent here
                startActivityForResult(Intent(context, ProfileEditActivity::class.java), 0)
                true
            }


    }

}
