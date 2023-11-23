package com.example.goodlook

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDao
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CardRepository
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentSearchBottomScreenBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchBottomScreen : BottomSheetDialogFragment() {
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: FragmentSearchBottomScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding
        binding = FragmentSearchBottomScreenBinding.inflate(inflater, container, false)


        // Implement viewModel
        val application = requireNotNull(this.activity).application


        // Implement viewModel for FavorFragmentViewModel
        val cardDataSource = CardDatabase.getInstance(application)!!.cardDao()
        val cardVmFactory = VmFactory(cardDataSource, application, FavorFragmentViewModel::class.java)
        val vm = ViewModelProvider(this, cardVmFactory).get(FavorFragmentViewModel::class.java)

        //Get list
        val recyclerView = binding.recyclerView
        itemAdapter = ItemAdapter(vm)
        recyclerView.adapter = itemAdapter

        vm.filteredCards.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        itemAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)

        //OffsetDecoration
        val offsetDecoration = OffsetDecoration(start = 16, top = 16, end = 15, bottom = 16)
        recyclerView.addItemDecoration(offsetDecoration)

        //Search
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(text: String?): Boolean {
                if (text.isNullOrEmpty()) {
                    recyclerView.visibility = View.GONE
                } else {

                    recyclerView.visibility = View.VISIBLE
                    vm.onQueryTextChange(text)

                }
                return true
            }
        })


        return binding.root
    }




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new BottomSheetDialog with the specified theme.
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)

        // Set an OnShowListener for the dialog to perform custom actions when shown.
        dialog.setOnShowListener { dialogInterface ->
            // Cast the dialog to a BottomSheetDialog to access its features.
            val bottomSheetDialog = dialogInterface as BottomSheetDialog

            // Find the root layout of the BottomSheetDialog.
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            // Make sure the layout is not null before proceeding.
            parentLayout?.let {
                // Get the BottomSheetBehavior associated with the layout.
                val behavior = BottomSheetBehavior.from(it)

                // Set the layout's height to MATCH_PARENT to take up the full screen height.
                setupFullHeight(it)

                // Set the state of the BottomSheetBehavior to STATE_EXPANDED to fully show the dialog.
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        // Return the customized BottomSheetDialog.
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        // Get the layout params of the view.
        val layoutParams = bottomSheet.layoutParams

        // Set the height to MATCH_PARENT to make the view take up the full height of the screen.
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

        // Apply the updated layout params to the view.
        bottomSheet.layoutParams = layoutParams
    }

}
