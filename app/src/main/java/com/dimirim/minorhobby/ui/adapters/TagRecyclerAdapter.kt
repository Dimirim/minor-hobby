package com.dimirim.minorhobby.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dimirim.minorhobby.databinding.ItemTagBinding
import com.dimirim.minorhobby.models.Tag

class TagRecyclerAdapter<T : ViewDataBinding>(
    private val onItemClickListener: OnItemClickListener,
    @LayoutRes private val layoutId: Int,
    private var items: List<Tag>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(items: List<Tag>) {
        this.items = items
        notifyDataSetChanged() // TODO : Use [DiffUtil]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (val binding = DataBindingUtil.inflate<T>(inflater, layoutId, parent, false)) {
            is ItemTagBinding -> TagViewHolder(binding, onItemClickListener)
            else -> throw TypeCastException()
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagViewHolder -> holder.bind(position, items[position])
        }
    }

    companion object {
        @BindingAdapter("bind:item")
        @JvmStatic
        fun bindItem(recyclerView: RecyclerView, items: ObservableArrayList<Tag>) {
            val adapter: TagRecyclerAdapter<ViewDataBinding> =
                recyclerView.adapter as TagRecyclerAdapter<ViewDataBinding>
            adapter.setItems(items)
        }
    }
}

class TagViewHolder(
    private val binding: ItemTagBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(position: Int, tag: Tag) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        binding.item = tag
    }
}
