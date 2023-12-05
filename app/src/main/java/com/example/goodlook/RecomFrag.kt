package com.example.goodlook

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.goodlook.basefragment.BaseDialogFragment
import com.example.goodlook.basefragment.createViewModel

import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databinding.FragmentRecomBinding
import com.example.goodlook.viewmodel.CategoryViewModel
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class RecomFrag : BaseDialogFragment<FragmentRecomBinding>(FragmentRecomBinding::inflate),
    DateClickListener, TimeClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var alarmIntent: PendingIntent
    private var alarmManager: AlarmManager? = null

    private lateinit var card_viewModel: FavorFragmentViewModel
    private lateinit var cat_viewModel: CategoryViewModel
    private var selected_category: CategoryEntity? = null
    private var interval: String ?= null

    private var calendar : Calendar = Calendar.getInstance()
    private var startTime : Long ?= null

    var year = 0
      var month = 0
      var day = 0
      var hour = 0
      var minute = 0



    //Give view to our methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //implement viewModel
        val application = requireNotNull(this.activity).application


        // Implement viewModel for FavorFragmentViewModel
        val card_dataSource = CardDatabase.getInstance(application)!!.cardDao()
        card_viewModel = createViewModel(application, card_dataSource)

        //cat viewmodel
        val cat_dataSource = CategoryDatabase.getInstance(application)!!.categoryDao()
        cat_viewModel = createViewModel(application, cat_dataSource)


        //Spinner
        val spinner = binding.spinner

        cat_viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            // Update the Spinner with the list of categories
            val adapter = ArrayAdapter(requireContext(), android.R.layout.preference_category, categories)
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



        val repeating = binding.repeating
        val intervals =
            arrayListOf(
                getString(R.string.daily),
                getString(R.string.weekly),
                getString(R.string.monthly)
            )

        val repeating_adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, intervals)


        repeating_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        repeating.adapter = repeating_adapter

        repeating.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define your actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                interval = intervals[position]
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
            val cardCategory_id = selected_category?.id
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
                    val uuid = UUID.randomUUID()
                    val requestCode = uuidToInt(uuid)
                    if (cardCategory_id != null) {
                        card_viewModel.onClickInsert(newCardTask, deadline, cardCategory_id,requestCode)
                    }

                    Toast.makeText(context,"Succesfully added new $newCardTask card.", Toast.LENGTH_SHORT).show()

                    alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                        intent.putExtra("ToDo", "$newCardTask")
                        PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
                    }


                    val interval_time: Long = when {
                        interval.equals(getString(R.string.monthly)) -> AlarmManager.INTERVAL_DAY * 30 // Monthly
                        interval.equals(getString(R.string.weekly)) -> AlarmManager.INTERVAL_DAY * 7   // Weekly
                        else -> AlarmManager.INTERVAL_DAY                                  // Daily

                    }



                    startTime?.let {
                        alarmManager?.setInexactRepeating(
                            AlarmManager.RTC_WAKEUP,
                            it,
                            interval_time,
                            alarmIntent
                        )
                    }


                }
            } catch (e: DateException) {
                // handle the error gracefully
                Toast.makeText(context,"${e.message}", Toast.LENGTH_SHORT).show()

            }




        }






    }




    fun uuidToInt(uuid: UUID): Int {
        return uuid.hashCode()
    }

    //To catch the error
    class DateException(message: String) : Exception(message)



    override fun onDateClick(v: View) {

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)




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


        startTime = calculateStartTime(year, month, day, hourOfDay, minute)


    }

    fun calculateStartTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0) // Set seconds to zero for precision

        return calendar.timeInMillis
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new BottomSheetDialog with the specified theme.
        val dialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialog)

        // Set an OnShowListener for the dialog to perform custom actions when shown.
        dialog.setOnShowListener { dialogInterface ->
            // Cast the dialog to a BottomSheetDialog to access its features.
            val bottomSheetDialog = dialogInterface as BottomSheetDialog

            // Find the root layout of the BottomSheetDialog.
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            // Make sure the layout is not null before proceeding.
            parentLayout?.let {
                // Get the BottomSheetBehavior associated with the layout.
                val behavior = BottomSheetBehavior.from(it)

                // Set the layout's height to MATCH_PARENT to take up the full screen height.
                setupFullHeight(it)

                // Set the state of the BottomSheetBehavior to STATE_EXPANDED to fully show the dialog.
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        // Return the customized BottomSheetDialog.
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        // Get the layout params of the view.
        val layoutParams = bottomSheet.layoutParams

        // Set the height to MATCH_PARENT to make the view take up the full height of the screen.
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

        // Apply the updated layout params to the view.
        bottomSheet.layoutParams = layoutParams
    }

    companion object{

        @JvmStatic val TAG = RecomFrag::class.java.simpleName
    }
}








