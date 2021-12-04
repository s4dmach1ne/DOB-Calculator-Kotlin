package com.rey.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInSeconds : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvAgeInHours : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInSeconds = findViewById(R.id.tvAgeInSeconds)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)

        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }

    }
    // private to make it not used by other class/file (can only be used in main activity)
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,R.style.datepicker,{
                view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                // tvSelectedDate?.setText(selectedDate)
                tvSelectedDate?.text = selectedDate

                // https://developer.android.com/reference/kotlin/java/text/SimpleDateFormat
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                // converts selectedDate into theDate format
                val theDate = sdf.parse(selectedDate)

                // if date is not null, run the code (null safety)
                theDate?.let{

                    // date to seconds,minutes,hours
                    // theDate.time properties passed ever since 1 January 1970 00.00am in milliseconds
                    // milliseconds to proper seconds, minutes, and hours
                    val selectedDateInSeconds = theDate.time / 1000
                    val selectedDateInMinutes = theDate.time / 60000
                    val selectedDateInHours = theDate.time / 3600000

                    // System.currentTimeMillis() getting current dates
                    // time properties passed ever since 1 January 1970 00.00am in milliseconds
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    // if current date is not null, run the code (null safety)
                    currentDate?.let{

                        // date to seconds,minutes,hours
                        // time properties passed ever since 1 January 1970 00.00am in milliseconds
                        // milliseconds to proper seconds, minutes, and hours
                        val currentDateInSeconds = currentDate.time / 1000
                        val currentDateInMinutes = currentDate.time / 60000
                        val currentDateInHours = currentDate.time / 3600000

                        // subtract current date to selected DOB
                        val differenceInSeconds = currentDateInSeconds - selectedDateInSeconds
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInHours = currentDateInHours - selectedDateInHours

                        // set the text on the main_layout.xml      also convert Long to String
                        tvAgeInSeconds?.text = differenceInSeconds.toString()
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvAgeInHours?.text = differenceInHours.toString()
                    }
                }
            },year,month,day
        )
        // Makes date picker only select yesterday dates        24hrs
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}