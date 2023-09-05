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
import com.example.goodlook.viewmodel.CategoryVmFactory

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
        val dataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        val vmFactory = CategoryVmFactory(dataSource,application)
        val vm = ViewModelProvider(this,vmFactory).get(CategoryViewModel::class.java)

        binding.saveCard.setOnClickListener {
            val newCardTask =binding.newCardTask.text.toString()

            vm.onInsertCategory(newCardTask)

            Toast.makeText(context,"Succesfully added new $newCardTask category.", Toast.LENGTH_LONG).show()

            dismiss()

        }
        return  binding.root
    }
    companion object{
        @JvmStatic val TAG = AddListFragment::class.java.simpleName
    }

}