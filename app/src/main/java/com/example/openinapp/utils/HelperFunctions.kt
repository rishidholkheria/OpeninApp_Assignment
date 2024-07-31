package com.example.openinapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HelperFunctions {

    fun getDate(createdAt: Long): String {
        val simpleDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateInLong = createdAt.toLong()
        val createdDate = Date(dateInLong)

        return simpleDate.format(createdDate)
    }
}