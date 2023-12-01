package com.example.goodlook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.OnItemLongClickListener
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.ParentAdapterBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel

class CategoryAdapter(private val viewModel: FavorFragmentViewModel,
                      private val cat_viewmodel : CategoryViewModel

) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(), OnItemLongClickListener {

    var itemClick: ((CategoryEntity) -> Unit)? = null

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
        return ViewHolder(binding,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]

        holder.bind(section)
    }

    override fun getItemCount(): Int {
        return sections.size
    }



    inner class ViewHolder(private val binding: ParentAdapterBinding, val itemClick: ((CategoryEntity) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {





        private val sectionTitleTextView = binding.contentTitle


        fun bind(category: CategoryEntity) {
            sectionTitleTextView.text = category.categoryName



            itemView.setOnClickListener {

                itemClick?.invoke(category)

                onItemLongClickListener?.onItemLongClick(adapterPosition)
                true
            }



        }
    }

    override fun onItemLongClick(position: Int) {
        // Remove the item from the dataset
        cat_viewmodel.onDeleteCategory(sections[position])

        // Notify the adapter about the item removal
        notifyItemRemoved(position)
    }


}


