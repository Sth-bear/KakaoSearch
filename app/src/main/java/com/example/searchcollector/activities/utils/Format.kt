package com.example.searchcollector.activities.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}