package com.example.goodlook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodlook.database.CardEntity
import com.example.goodlook.view.ItemAdapter


class FavorFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favor, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val cards = ArrayList<CardEntity>()
        cards.add(CardEntity(1,"Task1", 15, "ASDFASF"))


        //Adapter
        val itemAdapter = ItemAdapter()


        recyclerView.apply {

            recyclerView.adapter = itemAdapter
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)
        }

        itemAdapter.submitList(cards)
        return view
    }


}