package com.example.trishmuk.slashed.Controller.Utilities

import android.content.Context
import com.android.volley.toolbox.Volley

class Prefers(context: Context) {
    val PREFS_FILENAME = "prefs"
    val prefers = context.getSharedPreferences(PREFS_FILENAME, 0)

    val IS_LOGGED_IN = "isloggedin"
    val AUTH_TOKEN = "authToken"
    val USER_EMAIL = "Email"

    var isLoggedin: Boolean
        get() = prefers.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefers.edit().putBoolean(IS_LOGGED_IN, value).apply()
    var authToken: String
        get() = prefers.getString(AUTH_TOKEN,"")
        set(value) = prefers.edit().putString(AUTH_TOKEN, value).apply()
    var userEmail: String
        get() = prefers.getString(USER_EMAIL, "")
        set(value) = prefers.edit().putString(USER_EMAIL, value).apply()

    val request = Volley.newRequestQueue(context)
}