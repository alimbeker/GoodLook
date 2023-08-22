package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel

class KorzinAdapter(private val viewModel: FavorFragmentViewModel) : RecyclerView.Adapter<KorzinAdapter.CardViewHolder>() {

    private var cardList: List<CardEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.itemView.findViewById<ImageView>(R.id.listImage).setOnClickListener {

            holder.itemView.findViewById<ImageView>(R.id.listImage).setImageResource(R.drawable.checked)

            viewModel.onChecked(card)

        }
        holder.bind(card)
    }

    override fun getItemCount(): Int = cardList.size

    fun updateData(newCardList: List<CardEntity>) {
        cardList = newCardList
        notifyDataSetChanged()
    }

    inner class CardViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardEntity) {
            binding.listName.text = card.cardName
            //to print
            binding.listTime.text = card.getFormattedDeadline()

        }
    }
}
