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
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.CategoryVmFactory
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory
import java.util.*
import java.util.concurrent.TimeUnit


class RecomFrag : Fragment(),
    DateClickListener, TimeClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var alarmIntent: PendingIntent
    private var alarmManager: AlarmManager? = null
    private var calendar : Calendar = Calendar.getInstance()
    private lateinit var binding: FragmentRecomBinding
    private lateinit var vm: FavorFragmentViewModel

      var year = 0
      var month = 0
      var day = 0
      var hour = 0
      var minute = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRecomBinding.inflate(inflater, container, false)



        //implement viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = CardDatabase.getInstance(application)!!.cardDao()
        val vmFactory = VmFactory(dataSource,application)
        vm = ViewModelProvider(this,vmFactory).get(FavorFragmentViewModel::class.java)

        //cat viewmodel
        val cat_dataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        val cat_vmFactory = CategoryVmFactory(cat_dataSource, application)
        val cat_viewModel = ViewModelProvider(this, cat_vmFactory).get(CategoryViewModel::class.java)



        //Spinner
        val spinner = binding.spinner

        cat_viewModel.allCards.observe(viewLifecycleOwner) { categories ->
            // Update the Spinner with the list of categories
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                val selectedObject = spinner.selectedItem as CategoryEntity

                binding.category.text = selectedObject.categoryName

            }
        }
        // SET VALUE TO ALARMMANAGER
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager



        binding.saveCard.setOnClickListener {
            val newCardTask = binding.newCardTask.text.toString()
            val cardCategory = binding.category.text.toString()
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



        return binding.root
    }

    //Give view to our methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Observe the LiveData in your Activity or Fragment\



        binding.txtDate.setOnClickListener {
                view -> onDateClick(view)
        }
        binding.txtTime.setOnClickListener {
                view -> onTimeClick(view)
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




