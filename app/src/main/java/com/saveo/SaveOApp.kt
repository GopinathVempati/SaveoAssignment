package com.saveo

import android.app.Application
import android.content.Context

class SaveOApp : Application() {

    companion object { var appContext: Context? = null }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}