package com.poronga.batovi

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.poronga.batovi.dagger.component.AppComponent
import com.poronga.batovi.dagger.component.DaggerAppComponent
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.Task
import com.poronga.batovi.model.json.User
import com.poronga.batovi.services.UserManager

open class App: Application() {
    override fun onCreate() {
        instance = this
        injector = DaggerAppComponent.builder().application(this@App).build()
        val gsonUser = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_USER, null)
        if(gsonUser != null) currentUser = Gson().fromJson(gsonUser, User::class.java)
        super.onCreate()
    }

    companion object{
        lateinit var instance: App
        lateinit var injector: AppComponent
        var currentUser: User? = null
        lateinit var projects: MutableList<Project>
        lateinit var recentProjects:MutableList<Project>
    }
}