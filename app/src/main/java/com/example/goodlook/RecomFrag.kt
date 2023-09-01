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
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.FragmentRecomBinding
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


        //Spinner
        val spinner = binding.spinner

        val spinnerObj = getCategoryObjects()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, spinnerObj)

        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                val selectedObject = spinner.selectedItem as CategoryEntity

                binding.category.text = selectedObject.categoryName

            }
        }


        binding.saveCard.setOnClickListener {
            val newCardTask = binding.newCardTask.text.toString()
            val cardCategory = binding.category.text.toString()
              //Calendar
            val c = Calendar.getInstance()
            c.set(year,month,day,hour,minute)

            val today = Calendar.getInstance()

            val deadline = c.timeInMillis/1000L

            val sysdate = today.timeInMillis/1000L



            try {
                // some code that might throw an exception
                if (deadline < sysdate || newCardTask.isEmpty()) {
                    throw DateException("Something went wrong with date")

                } else {
                    vm.onClickInsert(newCardTask, deadline,cardCategory)

                    Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()
//                    val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                        .setInitialDelay(15, TimeUnit.SECONDS)
//                        .setInputData(
//                            workDataOf(
//                                "title" to "Todo Created",
//                                "message" to "A new todo has been created!")
//                        )
//                        .build()
//                    WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
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
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Observe the LiveData in your Activity or Fragment
//        vm.filteredCards.observe(viewLifecycleOwner, { cardList ->
//            if (cardList != null) {
//                for (card in cardList) {
//                    alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
//                        PendingIntent.getBroadcast(context, card.id, intent, PendingIntent.FLAG_IMMUTABLE)
//                    }
//                }
//            }
//        })
//



        binding.txtDate.setOnClickListener {
                view -> onDateClick(view)
//                alarmManager?.setInexactRepeating(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
//                AlarmManager.INTERVAL_HALF_HOUR,
//                alarmIntent
//                )
        }
        binding.txtTime.setOnClickListener {
                view -> onTimeClick(view)
        }

    }



    private fun getCategoryObjects(): ArrayList<CategoryEntity> {
        val customObjects = ArrayList<CategoryEntity>()
        customObjects.apply {
            add(CategoryEntity(0,"Groceries"))
            add(CategoryEntity(1,"Work"))
            add(CategoryEntity(2,"Personal"))

        }
        return customObjects
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
        const val DAILY_ALARM_REQUEST_CODE = 1001
        const val WEEKLY_ALARM_REQUEST_CODE = 1002
        const val MONTHLY_ALARM_REQUEST_CODE = 1003
        @JvmStatic val TAG = RecomFrag::class.java.simpleName
    }
}




