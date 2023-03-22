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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory
import java.util.concurrent.TimeUnit


class RecomFrag : Fragment() {
      private lateinit var dataBinding: FragmentRecomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
/*
        var rootView:View = inflater.inflate(R.layout.fragment_recom,container,false)
*/
        dataBinding = FragmentRecomBinding.inflate(inflater, container, false)


        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource,application)
        val vm = ViewModelProvider(this,vmFactory).get(FavorFragmentViewModel::class.java)
        dataBinding.saveCard.setOnClickListener {
            val newCardTask = dataBinding.newCardTask.text.toString()

            vm.onClickInsert(newCardTask,15)

       /*     vm.onClickInsert(newCardTask,newCardDesc)*/

            Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Todo Created",
                        "message" to "A new todo has been created!")
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)


        }



        return  dataBinding.root
    }

    /*override fun onButtonAddTodo(v: View) {
     return
    }*/

    companion object{
        @JvmStatic val TAG = RecomFrag::class.java.simpleName
    }
    }


