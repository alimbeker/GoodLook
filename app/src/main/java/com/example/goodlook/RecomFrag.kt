package com.example.goodlook

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory
import java.util.*


class RecomFrag : BaseFragment<FragmentRecomBinding>(FragmentRecomBinding::inflate),
    DateClickListener, TimeClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var alarmIntent: PendingIntent
    private var alarmManager: AlarmManager? = null
    private var calendar : Calendar = Calendar.getInstance()
    private lateinit var vm: FavorFragmentViewModel
    private var selected_category: CategoryEntity? = null
    var year = 0
      var month = 0
      var day = 0
      var hour = 0
      var minute = 0



    //Give view to our methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //implement viewModel
        // Implement viewModel
        val application = requireNotNull(this.activity).application


        // Implement viewModel for FavorFragmentViewModel
        val cardDataSource = CardDatabase.getInstance(application)!!.cardDao()
        val cardVmFactory = VmFactory(cardDataSource, application, FavorFragmentViewModel::class.java)
        vm = ViewModelProvider(this, cardVmFactory).get(FavorFragmentViewModel::class.java)

        //cat viewmodel
        val categoryDataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        val categoryVmFactory = VmFactory(categoryDataSource, application, CategoryViewModel::class.java)
        val cat_viewModel = ViewModelProvider(this, categoryVmFactory).get(CategoryViewModel::class.java)



        //Spinner
        val spinner = binding.spinner

        cat_viewModel.allCards.observe(viewLifecycleOwner) { categories ->
            // Update the Spinner with the list of categories
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
            spinner.adapter = adapter
        }




        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                selected_category = spinner.selectedItem as CategoryEntity



            }
        }
        // SET VALUE TO ALARMMANAGER
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager



        binding.txtDate.setOnClickListener {
               onDateClick(it)
        }
        binding.txtTime.setOnClickListener {
               onTimeClick(it)
        }

        binding.saveCard.setOnClickListener {
            val newCardTask = binding.newCardTask.text.toString()
            val cardCategory = selected_category.toString()
            //Calendar
            val c = Calendar.getInstance()
            c.set(year,month,day,hour,minute)

            val deadline = c.timeInMillis/1000L

            val sysdate = Calendar.getInstance().timeInMillis/1000L

            val diffDays = (deadline - sysdate) / (60 * 60 * 24)



            try {
                // some code that might throw an exception
                if (deadline < sysdate || newCardTask.isNullOrBlank()) {
                    throw DateException("Something went wrong with date")

                } else {
                    val requestCode = vm.generateUniqueRequestId()
                    vm.onClickInsert(newCardTask, deadline,cardCategory,requestCode)

                    Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()

                    alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                        intent.putExtra("ToDo", "$newCardTask")
                        PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
                    }


                    val interval: Long = when {
                        diffDays > 30 -> AlarmManager.INTERVAL_DAY * 30 // Monthly
                        diffDays > 7 -> AlarmManager.INTERVAL_DAY * 7   // Weekly
                        else -> AlarmManager.INTERVAL_DAY               // Daily
                    }

                    alarmManager?.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime(),
                        interval,
                        alarmIntent
                    )


                }
            } catch (e: DateException) {
                // handle the error gracefully
                Toast.makeText(context,"${e.message}", Toast.LENGTH_SHORT).show()

            }




        }




    }






    //To catch the error
    class DateException(message: String) : Exception(message)



    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)




        activity?.let { DatePickerDialog(it, this, year,month,day).show() }


    }





    override fun onTimeClick(v: View) {

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)



        TimePickerDialog(activity,this,hour,minute,DateFormat.is24HourFormat(activity)).show()

    }




    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year,month,day)
            binding.txtDate.setText(day.toString().padStart(2, '0')
                    + "-" + (month+1).toString().padStart(2, '0') + "-" + year)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        binding.txtTime.setText(
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




