package com.example.goodlook.view

import android.os.Handler
import android.os.Looper
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


open class ItemAdapter(private val vm : FavorFragmentViewModel):ListAdapter<CardEntity,ItemAdapter.ViewHolder>(CardDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card,vm)
    }

    class ViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(card:CardEntity, vm: FavorFragmentViewModel) {
            binding.listName.text = card.cardName
            //to print
            binding.listTime.text = card.getFormattedDeadline()
            binding.listImage.setOnClickListener {
                binding.listImage.setImageResource(R.drawable.checked)


                Handler(Looper.getMainLooper()).postDelayed({
                    // Perform the action after the delay
                    vm.onChecked(card)
                }, 1000)
            }
        }
        companion object{
            fun create (parent:ViewGroup):ViewHolder{
                return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }

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



