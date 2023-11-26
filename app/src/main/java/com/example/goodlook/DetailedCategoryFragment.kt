package com.example.goodlook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databinding.FragmentDetailedCategoryBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory

class DetailedCategoryFragment : Fragment() {
    private lateinit var itemAdapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailedCategoryBinding.inflate(inflater, container, false)

        val args = DetailedCategoryFragmentArgs.fromBundle(requireArguments())
        binding.contentTitle.text = args.title

        // Implement viewModel
        val application = requireNotNull(this.activity).application


        // Implement viewModel for FavorFragmentViewModel
        val cardDataSource = CardDatabase.getInstance(application)!!.cardDao()
        val cardVmFactory = VmFactory(cardDataSource, application, FavorFragmentViewModel::class.java)
        val vm = ViewModelProvider(this, cardVmFactory).get(FavorFragmentViewModel::class.java)

        //Get list
        val itemRecyclerView = binding.childRecyclerView
        itemAdapter = ItemAdapter(vm)
        itemRecyclerView.adapter = itemAdapter

        vm.filteredCards.observeForever { allCards ->
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






        return binding.root

    }


}