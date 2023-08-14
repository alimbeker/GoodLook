package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.ListItemBinding



class ItemAdapter:ListAdapter<CardEntity,ItemAdapter.ViewHolder>(CardDiffCallback()){

    class ViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(card:CardEntity)=with(binding){
            listName.text = card.cardName
            //to print
            listTime.text = card.getFormattedDeadline()
            listImage.setOnClickListener {
                val favoriteDrawable = if (card.isFavorite) {
                    R.drawable.ic_baseline_favorite_border_24 // Your black favorite icon drawable
                } else {
                    R.drawable.ic_baseline_favorite_24 // Your red favorite icon drawable
                }
                listImage.setImageResource(favoriteDrawable)

                // Toggle the favorite status of the card
                card.isFavorite = !card.isFavorite
            }

        }
        companion object{
            fun create (parent:ViewGroup):ViewHolder{
                return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class CardDiffCallback: DiffUtil.ItemCallback<CardEntity>() {
    override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
        return oldItem==newItem
    }
}


/*

class ItemAdapter : ListAdapter<CardEntity, ItemAdapter.ViewHolder>(CardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CardEntity) {
            binding.listItem = card
            binding.executePendingBindings()
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
*/
