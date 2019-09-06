package com.poronga.batovi.view.ui.main

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poronga.batovi.*
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.User
import com.poronga.batovi.view.adapter.ProjectAdapter
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    @Inject
    lateinit var gson: Gson
    lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //LOAD VIEW MODEL
        model = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]
        App.projects = model.projects
        //INJECT
        App.injector.inject(this@MainActivity)
        //CHECK IF USER IS FOUND, IF NOT, ASK.
        if (getUser() == null){
            //Create user
            newUser()
        } else {
            onUserExists()
        }

        //SET ONCLICK LISTENERS
        btnNewProject.setOnClickListener {
            askDifficulty()
        }
    }

    fun askDifficulty(){
        val dialog = Dialog(this)
        with(dialog) {
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener {
                newProjectDialog(1)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener {
                newProjectDialog(2)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener {
                newProjectDialog(3)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener {
                newProjectDialog(4)
                dismiss()
            }
            show()
        }
    }

    fun newProjectDialog(difficulty: Int){
        //SHOWS NEW FULLSCREEN DIALOG TO CREATE PROJECT WITH GIVEN DIFFICULTY
        Toast.makeText(this@MainActivity, "TODO (New Project Dialog) diff=$difficulty", Toast.LENGTH_SHORT).show()
    }

    fun newUser(){
        MaterialDialog(this@MainActivity, MaterialDialog.DEFAULT_BEHAVIOR).show{
            input(hint = "What's your name?", maxLength = 20, allowEmpty = false) { dialog, text ->
                createUser(text.toString())
                onUserExists()
                dialog.dismiss()
            }
        }
    }

    fun createUser(name: String){
        val user = User(id = 1, name = name)
        val userJson = gson.toJson(user)
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_USER, userJson)
            .apply()
    }

    fun getUser(): User?{
        val userJson = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_USER, null)
        if(userJson.isNullOrEmpty()) return null
        return gson.fromJson<User>(userJson, User::class.java)
    }

    fun saveProjects(){
        val projects = App.projects
        val projectsJson = gson.toJson(projects)
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_PROJECTS, projectsJson)
            .apply()
    }

    fun getProjects(): MutableList<Project>?{
        val projectsJson = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PREF_PROJECTS, null)
        if(projectsJson.isNullOrEmpty()) return null
        val projectsType = object : TypeToken<MutableList<Project>>() {}.type
        return gson.fromJson(projectsJson, projectsType)
    }

    fun onUserExists(){
        App.currentUser = getUser()!!
        Toast.makeText(this@MainActivity, App.currentUser.name, Toast.LENGTH_SHORT).show()
        saveProjects()
        //FIX !!
        model.projects = getProjects()!!
        adapter = ProjectAdapter(this@MainActivity) {
            Toast.makeText(this@MainActivity, "You clicked on ${it.name}", Toast.LENGTH_SHORT).show()
        }
        val lm = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        val decor = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
        adapter.setItems(model.projects)
        recycler_projects.layoutManager = lm
        recycler_projects.addItemDecoration(decor)
        recycler_projects.adapter = adapter
    }
}
