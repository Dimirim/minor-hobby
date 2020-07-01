package com.dimirim.minorhobby.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dimirim.minorhobby.R
import com.dimirim.minorhobby.ui.profile_edit.ProfileEditActivity

class MainPreference : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        val myPref =
            findPreference("key_profile_edit") as Preference?
        myPref!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { //open browser or intent here
                startActivityForResult(Intent(context, ProfileEditActivity::class.java), 0)
                true
            }
    }

}
