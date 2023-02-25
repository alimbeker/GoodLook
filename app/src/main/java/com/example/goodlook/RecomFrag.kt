package com.example.goodlook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.TimeUnit


class RecomFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView:View = inflater.inflate(R.layout.fragment_recom,container,false)


        /*//implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource,application)
        val vm = ViewModelProvider(this,vmFactory).get(HomeFragmentView::class.java)
*/
        rootView.findViewById<Button>(R.id.saveCard).setOnClickListener {
            val newCardTask = rootView.findViewById<EditText>(R.id.newCardTask).text.toString()


          /*  vm.onClickInsert(newCardTask,newCardDesc)*/

            Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()

        }

        /*rootView.findViewById<ImageView>(R.id.more).setOnClickListener {
            vm.onClickDelete()
        }*/


        return  rootView
    }
    }


