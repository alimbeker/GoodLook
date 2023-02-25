package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.ListItemBinding


class ItemAdapter : ListAdapter<CardEntity, ItemAdapter.ViewHolder>(CardDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CardEntity) {
            binding.listItem = card
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class CardDiffCallback : DiffUtil.ItemCallback<CardEntity>() {

    override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
        return oldItem == newItem
    }


}