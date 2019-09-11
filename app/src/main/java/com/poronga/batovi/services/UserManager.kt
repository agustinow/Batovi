package com.poronga.batovi.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poronga.batovi.*
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.User
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class UserManager(var onXPChanged: (Boolean) -> (Unit), var onAchievementGiven: (String) -> (Unit)) {
    @Inject
    lateinit var gson: Gson

    init {
        App.injector.inject(this)
    }

    fun createUser(name: String, image: String?){
        val user = User(id = 1, name = name, xp = 0, achievements = mutableListOf())
        if(image != null) user.image = image
        val userJson = gson.toJson(user)
        App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_USER, userJson)
            .apply()
    }

    fun saveUser(){
        val userJson = gson.toJson(App.currentUser)
        App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_USER, userJson)
            .apply()
    }

    fun getUser(): User?{
        val userJson = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_USER, null)
        if(userJson.isNullOrEmpty()) return null
        return gson.fromJson<User>(userJson, User::class.java)
    }

    fun saveProjects(){
        val projects = App.projects
        val projectsJson = gson.toJson(projects)
        App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_PROJECTS, projectsJson)
            .apply()
    }

    fun getProjects(): MutableList<Project>?{
        val projectsJson = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_PROJECTS, null)
        if(projectsJson.isNullOrEmpty()) return null
        val projectsType = object : TypeToken<MutableList<Project>>() {}.type
        return gson.fromJson(projectsJson, projectsType)
    }

    fun giveAchievementent(id:Int){
        if(!App.currentUser.achievements.contains(id)){
            App.currentUser.achievements.add(id)
            onAchievementGiven(baseAchievements[id].name!!)
            giveXp(baseAchievements[id].xp)
            saveUser()
        }
    }

    fun giveXp(value:Int){
        App.currentUser.xp += value
        if(App.currentUser.xp >= 100){
            App.currentUser.lvl++
            App.currentUser.xp -= 100
            onXPChanged(true)
        } else onXPChanged(false)
    }
}

