package com.app.kutumb.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtility {


    companion object{
        const val SMS_DATE_PATTERN = "dd MMMM yyyy, hh:mm a"


        @SuppressLint("SimpleDateFormat")
        fun getDateFromTime(pattern: String, time: Long): String {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            return formatter.format(Date(time))
        }
    }
}