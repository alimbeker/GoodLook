package com.example.goodlook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentKorzinBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory


class KorzinFrag : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentKorzinBinding.inflate(inflater, container, false)


        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource, application)
        val vm = ViewModelProvider(this, vmFactory).get(FavorFragmentViewModel::class.java)


        //Adapter
        val recyclerView = binding.korzinRecyclerView
        val itemAdapter = ItemAdapter(vm)
        recyclerView.adapter = itemAdapter

//        vm.filteredCards.observeForever {
//            // Filter favorCards based on the category or any other logic
//            val filteredFavorCards = it.filter { it.cardCategory == true }
//            itemAdapter.submitList(filteredFavorCards)
//        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        itemAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)

        return binding.root

    }


}