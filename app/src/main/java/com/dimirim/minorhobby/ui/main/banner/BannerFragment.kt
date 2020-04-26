package com.dimirim.minorhobby.ui.main.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.R
import kotlinx.android.synthetic.main.fragment_banner.view.*

class BannerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_banner, container, false)
        Glide.with(layout.context).load(arguments!!.getString("image")).into(layout.imageView)
        return layout
    }

}
