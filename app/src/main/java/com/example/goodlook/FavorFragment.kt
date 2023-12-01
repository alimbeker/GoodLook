package com.example.goodlook


import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.view.CategoryAdapter
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory



class FavorFragment : BaseFragment<FragmentFavorBinding>(FragmentFavorBinding::inflate) {

    private val application = requireNotNull(this.activity).application
    private val dataSource = CategoryDatabase.getInstance(application)!!.categoryDao()

    private val cat_viewModel = createViewModel<CategoryViewModel>(application, dataSource)



    private lateinit var categoryAdapter: CategoryAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Adapter
        val recyclerView = binding.recyclerView
        categoryAdapter = CategoryAdapter(cat_viewModel)
        recyclerView.adapter = categoryAdapter

        recyclerView.layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)

        //For parentRecyclerView
       val offsetDecoration = OffsetDecoration(start = 4, top = 20, end = 2, bottom = 16)

       recyclerView.addItemDecoration(offsetDecoration)


        categoryAdapter.itemClick = {

            findNavController().navigate(
                FavorFragmentDirections.actionFavorFragmentToDetailedCategoryFragment(it.categoryName)

            )

        }


        // Dialog ADDLIST
        binding.addlist.setOnClickListener {
            showAddCardFragment()
        }


        binding.searchBar.setOnClickListener {
            showBottomSheet()
        }

        // Menu
//        binding.dotMenu.setOnClickListener {
//            showMenu()
//        }

        // Search Bar

        // Bottom Screen
//        binding.searchByCircle.setOnClickListener {
//            showBottomSheet()
//        }





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
//    private fun showMenu() {
//        val popupMenu = PopupMenu(requireContext(), binding.dotMenu)
//        val inflater: MenuInflater = popupMenu.menuInflater
//        inflater.inflate(R.menu.upmenu, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
//            when (item.itemId) {
//                R.id.remove_by_deadline -> {
//                    vm.deleteByDeadline()
//                    true
//                }
//                R.id.remove_all -> {
//                    vm.onClear()
//                    true
//                }
//
//                // Add more cases for other menu options if needed
//                else -> false
//            }
//        }
//        popupMenu.show()
//    }


}