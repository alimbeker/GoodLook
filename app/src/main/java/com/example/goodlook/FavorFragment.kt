package com.example.goodlook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.view.SwipeToDelete
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory


class FavorFragment : Fragment() {
    lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: FragmentFavorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavorBinding.inflate(inflater, container, false)
        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource, application)
        val vm = ViewModelProvider(this, vmFactory).get(FavorFragmentViewModel::class.java)
        val recyclerView = binding.recyclerView

        binding.deleteButton.setOnClickListener {
            vm.deleteByDeadline()
        }

        //Adapter
        itemAdapter = ItemAdapter()
        recyclerView.adapter = itemAdapter
        vm.filteredCards.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)



        }
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        itemAdapter.notifyDataSetChanged()

        recyclerView.setHasFixedSize(true)



        val swipeToDelete = object :SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val a = vm.filteredCards.value?.get(position)
                vm.onSwipeDelete(a)


            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)



        return binding.root
    }


}