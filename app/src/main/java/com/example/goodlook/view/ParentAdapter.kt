package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.ParentAdapterBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import java.util.ArrayList

class ParentAdapter(private val viewModel: FavorFragmentViewModel,
                    private val cat_viewmodel : CategoryViewModel

) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

//    private val sections: List<CategoryEntity> = getCategoryObjects()

    private var sections: List<CategoryEntity> = emptyList()

    init {
        cat_viewmodel.allCards.observeForever { categories ->
            sections = categories
            notifyDataSetChanged()
        }
    }

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
            add(CategoryEntity(0,"Groceries"))
            add(CategoryEntity(1,"Work"))
            add(CategoryEntity(2,"Personal"))

        }
        return customObjects
    }


}


