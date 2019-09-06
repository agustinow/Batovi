package com.poronga.batovi

import android.app.Application
import com.poronga.batovi.dagger.component.AppComponent
import com.poronga.batovi.dagger.component.DaggerAppComponent
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.User

class App: Application() {
    override fun onCreate() {
        instance = this
        injector = DaggerAppComponent.builder().application(this@App).build()
        super.onCreate()
    }

    companion object{
        lateinit var instance: App
        lateinit var injector: AppComponent
        lateinit var currentUser: User
        lateinit var projects: MutableList<Project>
    }
}