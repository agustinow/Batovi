package com.poronga.batovi.services

import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poronga.batovi.*
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.Task
import com.poronga.batovi.model.json.User
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class UserManager(var onXPChanged: (Boolean) -> (Unit), var onAchievementGiven: (String) -> (Unit), var onProjectCreated:(Project)->(Unit)) {
    @Inject
    lateinit var gson: Gson

    init {
        App.injector.inject(this)
    }

    fun createUser(name: String, image: String?){
        val user = User(id = 1, name = name, xp = 0, achievements = mutableListOf(), lvl = 1, image = image)
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

    fun createProject(project: Project){
        App.projects.add(project)
        saveProjects()
        onProjectCreated(project)
    }

    fun deleteProject(name: String){
        App.projects.forEach {
            if (it.name == name){
                App.projects.remove(it)
                saveProjects()
            }
        }
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
        return (gson.fromJson(projectsJson, projectsType))
    }

    fun removeTask(projectName: String, taskName: String): Boolean{
        val project = App.projects.find{
            it.name == projectName
        }
        if(project != null){
            val task = project.tasks!!.find {
                it.name == taskName
            }
            if(task != null){
                App.projects[App.projects.indexOf(project)].tasks!!.remove(task)
                return true
            }
            return false
        }
        return false
    }

    fun changeTaskCompletedStatus(project: Project, task: Task, status: Boolean): Boolean{
        val projectIndex = App.projects.indexOf(project) ?: return false
        val taskIndex = project.tasks?.indexOf(task) ?: return false
        App.projects[projectIndex].tasks!![taskIndex].completed = status
        return true
    }

    fun giveAchievementent(id:Int){
        if(!App.currentUser!!.achievements.contains(id)){
            App.currentUser!!.achievements.add(id)
            onAchievementGiven(baseAchievements[id].name!!)
            giveXp(baseAchievements[id].xp)
            saveUser()
        }
    }

    fun giveXp(value:Int){
        App.currentUser!!.xp += value
        if(App.currentUser!!.xp >= 100){
            App.currentUser!!.lvl++
            App.currentUser!!.xp -= 100
            onXPChanged(true)
        } else onXPChanged(false)
    }

    fun saveImageToStorage(image: Bitmap, userID: Int): String{
        val directory: File = ContextWrapper(App.instance.applicationContext).getDir("imageDir", Context.MODE_PRIVATE)
        val path = File(directory, "user_$userID.jpg")
        val fos = FileOutputStream(path)
        image.compress(Bitmap.CompressFormat.PNG, 100, fos)
        return directory.absolutePath
    }
}

