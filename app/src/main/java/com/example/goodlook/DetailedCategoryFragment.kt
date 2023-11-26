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
import com.example.goodlook.databinding.FragmentKorzinBinding
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory

class DetailedCategoryFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailedCategoryBinding.inflate(inflater, container, false)

        val args = DetailedCategoryFragmentArgs.fromBundle(requireArguments())
        binding.contentTitle.text = args.title







        return binding.root

    }


}