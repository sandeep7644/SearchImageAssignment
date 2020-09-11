package com.example.assignment.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    lateinit var dateFormat: SimpleDateFormat
    val dateformaManager = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    fun getFormattedDateTime(timeInMillis: Long): String {
        return dateformaManager.format(Date(timeInMillis))
    }
}