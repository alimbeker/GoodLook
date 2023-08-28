package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CategoryEntity
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.databinding.ParentAdapterBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel

class ParentAdapter(private val viewModel: FavorFragmentViewModel) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private val sections: List<CategoryEntity> = mutableListOf()
    private val itemAdapter: ItemAdapter = ItemAdapter(viewModel)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParentAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]
        holder.bind(section, itemAdapter)
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    inner class ViewHolder(private val binding: ParentAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        private val sectionTitleTextView = binding.contentTitle
        private val itemRecyclerView = binding.childRecyclerView

        fun bind(category: CategoryEntity, itemAdapter: ItemAdapter) {
            sectionTitleTextView.text = category.categoryName
            itemRecyclerView.adapter = itemAdapter
        }
    }
}
