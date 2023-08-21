package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel


class ItemAdapter(private val vm : FavorFragmentViewModel):ListAdapter<CardEntity,ItemAdapter.ViewHolder>(CardDiffCallback()){

    class ViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(card:CardEntity)=with(binding){
            listName.text = card.cardName
            //to print
            listTime.text = card.getFormattedDeadline()


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
        val card = getItem(position)
        holder.itemView.findViewById<ImageView>(R.id.listImage).setOnClickListener {

                vm.onChecked(card)

        }
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
