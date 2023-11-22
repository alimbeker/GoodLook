package com.example.goodlook


import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.view.ParentAdapter
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory



class FavorFragment : BaseFragment<FragmentFavorBinding>(FragmentFavorBinding::inflate) {
    private lateinit var vm: FavorFragmentViewModel
    private lateinit var parentAdapter: ParentAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Implement viewModel
        val application = requireNotNull(this.activity).application


        // Implement viewModel for FavorFragmentViewModel
        val cardDataSource = CardDatabase.getInstance(application)!!.cardDao()
        val cardVmFactory = VmFactory(cardDataSource, application, FavorFragmentViewModel::class.java)
        vm = ViewModelProvider(this, cardVmFactory).get(FavorFragmentViewModel::class.java)

// Implement viewModel for CategoryViewModel
        val categoryDataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        val categoryVmFactory = VmFactory(categoryDataSource, application, CategoryViewModel::class.java)
        val cat_viewModel = ViewModelProvider(this, categoryVmFactory).get(CategoryViewModel::class.java)

        // Adapter
        val recyclerView = binding.recyclerView
        parentAdapter = ParentAdapter(vm, cat_viewModel)
        recyclerView.adapter = parentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)



        parentAdapter.itemClick = { it1, it2 ->

            findNavController().navigate(
                FavorFragmentDirections.actionFavorFragmentToDetailedCategory(it1.categoryName),
                it2
            )

        }



        // Dialog ADDLIST
        binding.addlist.setOnClickListener {
            showAddCardFragment()
        }

        // Menu
        binding.dotMenu.setOnClickListener {
            showMenu()
        }

        // Search Bar

        // Bottom Screen
        binding.searchByCircle.setOnClickListener {
            showBottomSheet()
        }





    }

    private fun showAddCardFragment(){
        val dialogFragment = AddListFragment()
        dialogFragment.show(childFragmentManager,AddListFragment.TAG)
    }
    //BottomSheet
    private fun showBottomSheet() {
            val bottomSheetFragment = SearchBottomScreen()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    //Menu to delete by deadline
    private fun showMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.dotMenu)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.upmenu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.remove_by_deadline -> {
                    vm.deleteByDeadline()
                    true
                }
                R.id.remove_all -> {
                    vm.onClear()
                    true
                }

                // Add more cases for other menu options if needed
                else -> false
            }
        }
        popupMenu.show()
    }


}