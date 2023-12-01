package com.example.goodlook


import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.goodlook.basefragment.BaseFragment
import com.example.goodlook.basefragment.createViewModel
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.view.CategoryAdapter
import com.example.goodlook.viewmodel.CategoryViewModel


class FavorFragment : BaseFragment<FragmentFavorBinding>(FragmentFavorBinding::inflate) {


    private lateinit var cat_viewModel: CategoryViewModel


    private lateinit var categoryAdapter: CategoryAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = CategoryDatabase.getInstance(application)!!.categoryDao()

        cat_viewModel = createViewModel(application, dataSource)

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
                FavorFragmentDirections.actionFavorFragmentToDetailedCategoryFragment(it.id.toString(),it.categoryName)

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