package com.example.goodlook.view

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.R
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CategoryEntity
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.databinding.ParentAdapterBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import java.util.ArrayList

class ParentAdapter(private val viewModel: FavorFragmentViewModel
) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private val sections: List<CategoryEntity> = getCategoryObjects()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParentAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]
        val itemAdapter = ItemAdapter(viewModel)
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

            // Observe changes in favorCards LiveData and update itemAdapter
            viewModel.filteredCards.observeForever { allCards ->
                // Filter favorCards based on the category or any other logic
                val filteredFavorCards = allCards.filter { it.cardCategory == category.categoryName }
                itemAdapter.submitList(filteredFavorCards)
            }

            itemRecyclerView.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = itemAdapter
            }


        }
    }


    private fun getCategoryObjects(): ArrayList<CategoryEntity> {
        val customObjects = ArrayList<CategoryEntity>()
        customObjects.apply {
            add(CategoryEntity(0,"Groceries", viewModel.groceries))
            add(CategoryEntity(1,"Work", viewModel.work))
            add(CategoryEntity(2,"Personal", viewModel.personal))

        }
        return customObjects
    }


}


