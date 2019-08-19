package io.fajarca.movies.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime() : String{
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}