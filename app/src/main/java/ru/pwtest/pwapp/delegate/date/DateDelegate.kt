package ru.pwtest.pwapp.delegate.date

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateDelegate @Inject constructor() {

    private var format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    fun parseDate(date:String): Date {
        return  format.parse(date)
    }

    fun formatDate(date: Date): String {
        var timeFormat = "hh:mm:ss dd.MM.yy"
        val sdf = SimpleDateFormat(timeFormat, Locale.getDefault())
        return sdf.format(date)
    }
}