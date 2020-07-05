package com.dimirim.minorhobby.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.databinding.ItemImageBinding

class ImageRecyclerAdapter<T : ViewDataBinding>(
    private val onItemClickListener: OnItemClickListener,
    @LayoutRes private val layoutId: Int,
    private var items: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged() // TODO : Use [DiffUtil]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (val binding = DataBindingUtil.inflate<T>(inflater, layoutId, parent, false)) {
            is ItemImageBinding -> ImageViewHolder(binding, onItemClickListener)
            else -> throw TypeCastException()
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(position, items[position])
        }
    }

    companion object {
        @BindingAdapter("bind:item")
        @JvmStatic
        fun bindItem(recyclerView: RecyclerView, items: List<String>) {
            val adapter: ImageRecyclerAdapter<ViewDataBinding> =
                recyclerView.adapter as ImageRecyclerAdapter<ViewDataBinding>
            adapter.setItems(items)
        }
    }
}

class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(position: Int, image: String) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        Log.d("test", "bind()")
        if (image.isNotEmpty()) {
            Log.d("test", "image")
            Glide.with(context)
                .load(image)
                .into(binding.postImageView)
        }
    }
}
