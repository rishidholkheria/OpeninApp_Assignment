package com.example.openinapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HelperFunctions {

    fun getDate(createdAt: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(createdAt)
            if (date != null) outputFormat.format(date) else "Invalid Date"
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }
    fun handleEmptyData(data: String): String{
        return if(data.isEmpty() || data == "") "Not Available" else data
    }
}