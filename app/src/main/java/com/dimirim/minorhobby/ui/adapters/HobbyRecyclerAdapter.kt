package com.dimirim.minorhobby.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimirim.minorhobby.databinding.ItemHobbyLargeBinding
import com.dimirim.minorhobby.databinding.ItemHobbyRoundBinding
import com.dimirim.minorhobby.models.Hobby

class HobbyRecyclerAdapter<T : ViewDataBinding>(
    private val onItemClickListener: OnItemClickListener,
    @LayoutRes private val layoutId: Int,
    private var items: List<Hobby>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(items: List<Hobby>) {
        this.items = items
        notifyDataSetChanged() // TODO : Use [DiffUtil]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (val binding = inflate<T>(inflater, layoutId, parent, false)) {
            is ItemHobbyRoundBinding -> HobbyRoundViewHolder(binding, onItemClickListener)
            is ItemHobbyLargeBinding -> HobbyLargeViewHolder(binding, onItemClickListener)
            else -> throw TypeCastException()
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HobbyRoundViewHolder -> holder.bind(position, items[position])
            is HobbyLargeViewHolder -> holder.bind(position, items[position])
        }
    }

    companion object {
        @BindingAdapter("bind:item")
        @JvmStatic
        fun bindItem(recyclerView: RecyclerView, items: ObservableArrayList<Hobby>) {
            val adapter: HobbyRecyclerAdapter<ViewDataBinding> =
                recyclerView.adapter as HobbyRecyclerAdapter<ViewDataBinding>
            adapter.setItems(items)
        }
    }
}

class HobbyRoundViewHolder(private val binding: ItemHobbyRoundBinding,
                           private val onItemClickListener: OnItemClickListener)
    : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(position: Int, hobby: Hobby) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        binding.item = hobby
        Glide.with(context)
            .load(hobby.image)
            .into(binding.hobbyImage)
    }
}

class HobbyLargeViewHolder(
    private val binding: ItemHobbyLargeBinding,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(position: Int, hobby: Hobby) {
        binding.root.setOnClickListener { onItemClickListener.onItemClick(position) }
        binding.item = hobby
        Glide.with(context)
            .load(hobby.image)
            .into(binding.hobbyImage)
    }
}

