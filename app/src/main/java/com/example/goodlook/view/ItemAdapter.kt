package com.example.goodlook.view

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay


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
                showDeleteSnackbar(card,vm)
            }
        }

        private fun showDeleteSnackbar(card: CardEntity, vm: FavorFragmentViewModel) {
            val snackbar = Snackbar.make(
                binding.root,
                "${card.cardName} is deleted",
                Snackbar.LENGTH_LONG
            )


            snackbar.setActionTextColor(binding.root.resources.getColor(R.color.colorAccent)) // Change action text color

            snackbar.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if (event != DISMISS_EVENT_ACTION) {

                        vm.onDone(card)
                    }
                }
            })

            snackbar.show()
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





