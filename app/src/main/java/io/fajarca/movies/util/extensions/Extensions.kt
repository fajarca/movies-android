package io.fajarca.movies.util.extensions

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.toLocalizedDatetimeFormat(): String {
    if (this.isNullOrEmpty()) return "-"

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    try {

        val date = dateFormat.parse(this)
        return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}