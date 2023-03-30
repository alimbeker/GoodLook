package com.example.goodlook

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class RecomFrag : Fragment(),
    DateClickListener, TimeClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var dataBinding: FragmentRecomBinding


      var year = 0
      var month = 0
      var day = 0
      var hour = 0
      var minute = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentRecomBinding.inflate(inflater, container, false)

        //interface
        dataBinding.listenerDate = this
        dataBinding.listenerTime = this

        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource,application)
        val vm = ViewModelProvider(this,vmFactory).get(FavorFragmentViewModel::class.java)



        dataBinding.saveCard.setOnClickListener {
            val newCardTask = dataBinding.newCardTask.text.toString()
              //Calendar
            val c = Calendar.getInstance()
            c.set(year,month,day,hour,minute)

            val today = Calendar.getInstance()

            val deadline = c.timeInMillis/1000L

            val sysdate = today.timeInMillis/1000L


            try {
                // some code that might throw an exception
                if (deadline < sysdate) {
                    throw DateException("Something went wrong with date")
                } else {
                    vm.onClickInsert(newCardTask, deadline, sysdate)

                    Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()
                    val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                        .setInitialDelay(15, TimeUnit.SECONDS)
                        .setInputData(
                            workDataOf(
                                "title" to "Todo Created",
                                "message" to "A new todo has been created!")
                        )
                        .build()
                    WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
                }
            } catch (e: DateException) {
                // handle the error gracefully
                Toast.makeText(context,"${e.message}", Toast.LENGTH_SHORT).show()

            }




        }



        return dataBinding.root
    }
    class DateException(message: String) : Exception(message)


    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        hour = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)





    }
    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)




        TimePickerDialog(activity,this,hour,minute,DateFormat.is24HourFormat(activity)).show()


        activity?.let { DatePickerDialog(it, this, year,month,day).show() }


    }




    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year,month,day)
            dataBinding.txtDate.setText(day.toString().padStart(2, '0')
                    + "-" + (month+1).toString().padStart(2, '0') + "-" + year)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        dataBinding.txtTime.setText(
            hourOfDay.toString().padStart(2, '0') + ":"
                    + minute.toString().padStart(2, '0')
        )
        this.hour = hourOfDay
        this.minute = minute



    }

    companion object{
        @JvmStatic val TAG = RecomFrag::class.java.simpleName
    }
}


