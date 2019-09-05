package com.poronga.batovi

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //injector =
    }

    companion object{
        lateinit var instance: App
        //lateinit var injector:
    }
}