package com.example.trishmuk.slashed.Controller

import android.app.Application
import android.content.Context
import com.example.trishmuk.slashed.Controller.Utilities.Prefers

class App: Application() {

    companion object {
        lateinit var preference: Prefers
    }

    override fun onCreate() {
        preference = Prefers(applicationContext)
        super.onCreate()
    }
}