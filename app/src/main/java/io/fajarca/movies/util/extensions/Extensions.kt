package io.fajarca.movies.util.extensions

import java.util.*

/**
 * Return number with a thousand separator on it
 */
fun Long.addThousandSeparator() : String {
    val formattedNumber = String.format(Locale.US,"%,02d", this).replace(',', '.')
    val formattedNumberCommas = String.format(Locale.US,",00", this)
    return "$formattedNumber$formattedNumberCommas"
}
