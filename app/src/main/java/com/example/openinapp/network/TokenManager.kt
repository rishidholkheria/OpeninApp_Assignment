package com.example.openinapp.network

import android.content.Context
import com.example.openinapp.utils.Constants.PREFS_TOKEN_FILE
import com.example.openinapp.utils.Constants.USER_TOKEN

class TokenManager(context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

}