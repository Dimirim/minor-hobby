package com.dimirim.minorhobby.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.databinding.ItemPostBinding
import com.dimirim.minorhobby.databinding.ItemPostLargeBinding
import com.dimirim.minorhobby.models.Post

class PostRecyclerAdapter<T : ViewDataBinding>(
    private val onItemClickListener: OnItemClickListener,
    @LayoutRes private val layoutId: Int,
    private var items: List<Post>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(items: List<Post>) {
        this.items = items
        notifyDataSetChanged() // TODO : Use [DiffUtil]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (val binding = inflate<T>(inflater, layoutId, parent, false)) {
            is ItemPostBinding -> PostViewHolder(
                binding,
                onItemClickListener
            )
            is ItemPostLargeBinding -> PostLargeViewHolder(
                binding,
                onItemClickListener
            )
            else -> throw TypeCastException()
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(position, items[position])
            is PostLargeViewHolder -> holder.bind(position, items[position])
        }
    }
}

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(position: Int, post: Post) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        Glide.with(context)
            .load(post.images[0])
            .into(binding.thumbnailImageView)
    }
}

class PostLargeViewHolder(
    private val binding: ItemPostLargeBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(position: Int, post: Post) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        Glide.with(context)
            .load(post.images[0])
            .into(binding.thumbnailImageView)
    }
}

