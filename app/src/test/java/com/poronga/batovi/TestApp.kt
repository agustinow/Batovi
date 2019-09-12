package com.poronga.batovi

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.poronga.batovi.dagger.component.AppComponent
import com.poronga.batovi.dagger.component.DaggerAppComponent
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.User

open class TestApp: App() {
    override fun onCreate() {
        instance = this
        injector = DaggerAppComponent.builder().application(this@TestApp).build()
        currentUser = User(
            id = 1,
            name = "Testman",
            lvl = 4,
            xp = 47,
            image = "",
            achievements = mutableListOf(1, 4, 7)
        )
        super.onCreate()
    }

    companion object{
        lateinit var instance: App
        lateinit var injector: AppComponent
        var currentUser: User? = null
        var projects: MutableList<Project> = sampleProjects.toMutableList()
    }
}