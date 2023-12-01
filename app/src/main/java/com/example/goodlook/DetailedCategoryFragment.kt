package com.example.goodlook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databinding.FragmentDetailedCategoryBinding
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory

class DetailedCategoryFragment : BaseFragment<FragmentDetailedCategoryBinding>(FragmentDetailedCategoryBinding::inflate) {


    private lateinit var card_viewModel: FavorFragmentViewModel

    private lateinit var itemAdapter: ItemAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Safe args
        val args = DetailedCategoryFragmentArgs.fromBundle(requireArguments())
        binding.contentTitle.text = args.title


        // Implement viewmodel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()

        card_viewModel = createViewModel(application, dataSource)



        //Get list
        val itemRecyclerView = binding.childRecyclerView
        itemAdapter = ItemAdapter(card_viewModel)
        itemRecyclerView.adapter = itemAdapter

        card_viewModel.filteredCards.observeForever { allCards ->
            // Filter favorCards based on the category or any other logic
            val filteredFavorCards = allCards.filter { it.cardCategory == args.title }
            itemAdapter.submitList(filteredFavorCards)
        }

        itemRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = itemAdapter
        }


        //OffsetDecoration
        val offsetDecoration = OffsetDecoration(start = 16, top = 16, end = 15, bottom = 16)
        itemRecyclerView.addItemDecoration(offsetDecoration)


    }


}