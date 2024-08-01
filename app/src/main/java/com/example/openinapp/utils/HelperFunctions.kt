package com.example.openinapp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
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

    fun handleEmptyData(data: String): String {
        return if (data.isEmpty() || data == "") "Not Available" else data
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }


    fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when {
            hourOfDay in 0..11 -> "Good Morning"
            hourOfDay in 12..16 -> "Good Afternoon"
            hourOfDay in 17..20 -> "Good Evening"
            else -> "Good Night"
        }
    }
}