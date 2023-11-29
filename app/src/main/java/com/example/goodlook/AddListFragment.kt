package com.example.goodlook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databinding.FragmentAddListBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.VmFactory

class AddListFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = FragmentAddListBinding.inflate(inflater, container, false)

        binding.backCard.setOnClickListener {
            dismiss()
        }
        //implement viewModel
        val application = requireNotNull(this.activity).application

        val categoryDataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        val categoryVmFactory = VmFactory(categoryDataSource, application, CategoryViewModel::class.java)
        val cat_viewModel = ViewModelProvider(this, categoryVmFactory).get(CategoryViewModel::class.java)



        binding.saveCard.setOnClickListener {
            val newCardTask = binding.newCardTask.text.toString()

            if(!newCardTask.isNullOrBlank()) {
                cat_viewModel.onInsertCategory(newCardTask)
                Toast.makeText(context,"Succesfully added new $newCardTask category.", Toast.LENGTH_LONG).show()
                dismiss()
            } else {
                Toast.makeText(context,"You must give name to category", Toast.LENGTH_LONG).show()

            }



        }
        return  binding.root
    }
    companion object{
        @JvmStatic val TAG = AddListFragment::class.java.simpleName
    }

}