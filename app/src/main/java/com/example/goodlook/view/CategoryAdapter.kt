package com.example.goodlook.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.DialogDeleteConfirmationBinding
import com.example.goodlook.databinding.ParentAdapterBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel

class CategoryAdapter(private val cat_viewmodel : CategoryViewModel

) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var itemClick: ((CategoryEntity, FragmentNavigator.Extras) -> Unit)? = null

    private var sections: List<CategoryEntity> = emptyList()

    init {
        cat_viewmodel.allCategories.observeForever { categories ->
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



    inner class ViewHolder(private val binding: ParentAdapterBinding, val itemClick: ((CategoryEntity, FragmentNavigator.Extras) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {
        private val sectionTitleTextView = binding.contentTitle


        fun bind(category: CategoryEntity) {
            sectionTitleTextView.text = category.categoryName

            binding.contentTitle.transitionName = category.categoryName


            itemView.setOnClickListener {
                val extras = FragmentNavigatorExtras(

                    binding.contentTitle to "title"
                )
                itemClick?.invoke(category,extras)
            }

            itemView.setOnLongClickListener {
                showDeleteConfirmationDialog(binding.root.context, position)
                true
            }



        }


        fun showDeleteConfirmationDialog(context: Context, position: Int) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            val binding = DialogDeleteConfirmationBinding.inflate(LayoutInflater.from(context))

            alertDialogBuilder.setView(binding.root)

            val alertDialog = alertDialogBuilder.create()

            binding.btnConfirmDelete.setOnClickListener {
                cat_viewmodel.onDeleteCategory(sections[position])
                notifyItemChanged(position)
                alertDialog.dismiss()
            }

            binding.btnCancelDelete.setOnClickListener {
                // Dismiss the dialog
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

    }




}


