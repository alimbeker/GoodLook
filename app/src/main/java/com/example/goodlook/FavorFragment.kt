package com.example.goodlook


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.ListItemBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory



class FavorFragment : Fragment() {
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var binding: FragmentFavorBinding
    private lateinit var vm: FavorFragmentViewModel
    private lateinit var itemBinding: ListItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavorBinding.inflate(inflater, container, false)
         //to delete card
        itemBinding = ListItemBinding.inflate(inflater,container,false)
        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource, application)
        vm = ViewModelProvider(this, vmFactory).get(FavorFragmentViewModel::class.java)


        //Adapter
        val recyclerView = binding.recyclerView
        itemAdapter = ItemAdapter(vm)
        recyclerView.adapter = itemAdapter

        vm.filteredCards.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        itemAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)


        //Menu
        binding.dotMenu.setOnClickListener {
            showMenu()
        }

        //Search Bar

       //Bottom Screen
        binding.searchByCircle.setOnClickListener {
            showBottomSheet()
        }
       //delete by checked
        itemBinding.listImage.setOnClickListener {
           fun onCheck(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val a = vm.filteredCards.value?.get(position)
                vm.onChecked(a)
            }
        }






        return binding.root
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