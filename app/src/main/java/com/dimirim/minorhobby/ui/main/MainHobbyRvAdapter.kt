package com.dimirim.minorhobby.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimirim.minorhobby.R
import java.text.NumberFormat

class MainRvAdapter(
    val context: Context,
    val hobbyList: ArrayList<Hobby>,
    val itemClick: (Hobby) -> Unit
) : RecyclerView.Adapter<MainRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_hobby_rv_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(hobbyList[position], context)
    }

    inner class Holder(itemView: View?, itemClick: (Hobby) -> Unit) :
        RecyclerView.ViewHolder(itemView!!) {

        val hobbyPhoto = itemView?.findViewById<ImageView>(R.id.hobbyPhoto)
        val hobbyName = itemView?.findViewById<TextView>(R.id.hobbyName)
        val hobbyMemberCount = itemView?.findViewById<TextView>(R.id.hobbyCount)

        fun bind(hobby: Hobby, context: Context) {
            if (hobby.photo != "") {
                //TODO: 이미지 라이브러리 정해지면 수정해야함
                val resourceId =
                    context.resources.getIdentifier(hobby.photo, "drawable", context.packageName)
                hobbyPhoto?.setImageResource(resourceId)
            } else {
                hobbyPhoto?.setImageResource(R.mipmap.ic_launcher)
            }
            hobbyName?.text = hobby.name
            hobbyMemberCount?.text =
                "${NumberFormat.getIntegerInstance().format(hobby.memberCount)} 명"

            itemView.setOnClickListener { itemClick(hobby) }
        }
    }
}