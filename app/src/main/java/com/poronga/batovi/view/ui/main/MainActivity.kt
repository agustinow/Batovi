package com.poronga.batovi.view.ui.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.poronga.batovi.*
import com.poronga.batovi.model.json.Project
import com.poronga.batovi.model.json.User
import com.poronga.batovi.view.adapter.ProjectAdapter
import com.poronga.batovi.view.ui.main.fragments.MainAboutFragment
import com.poronga.batovi.view.ui.main.fragments.MainHomeFragment
import com.poronga.batovi.view.ui.main.fragments.MainInfoFragment
import com.poronga.batovi.view.ui.main.fragments.MainNewProjectFragment
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_difficulty.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    @Inject
    lateinit var gson: Gson
    lateinit var drawer: Drawer

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

    fun askDifficulty(){
        val dialog = Dialog(this@MainActivity)
        with(dialog) {
            setContentView(R.layout.dialog_difficulty)
            findViewById<ImageButton>(R.id.imgBtnJunior).setOnClickListener {
                newProjectDialog(DIFF_JUNIOR)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnNormal).setOnClickListener {
                newProjectDialog(DIFF_NORMAL)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnComplex).setOnClickListener {
                newProjectDialog(DIFF_COMPLEX)
                dismiss()
            }
            findViewById<ImageButton>(R.id.imgBtnProfesional).setOnClickListener {
                //newProjectDialog(DIFF_PRO)
                newProjectDialog(DIFF_PRO)
                dismiss()
            }
            show()
        }
    }

    fun newProjectDialog(difficulty: Int){
        model.newProjectDifficulty = difficulty
        loadFragment(3)
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
        saveProjects()
        model.projects = getProjects()!!
        loadDrawer()
        loadFragment(0)
    }

    fun loadDrawer(){
        val itemHome = PrimaryDrawerItem().withIdentifier(0).withName("Home")
        val itemAbout = PrimaryDrawerItem().withIdentifier(1).withName("Info")
        val itemInfo = PrimaryDrawerItem().withIdentifier(2).withName("About")
        val itemAdd = SecondaryDrawerItem().withIdentifier(3).withName("New Project")

        drawer = DrawerBuilder()
            .withActivity(this@MainActivity)
            .addDrawerItems(
                itemHome,
                itemAbout,
                itemInfo,
                itemAdd
            )
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    //DO STUFF
                    when(position){
                        0 -> loadFragment(0)
                        1 -> loadFragment(1)
                        2 -> loadFragment(2)
                        3 -> askDifficulty()
                    }
                    return false
                }

            })
            .build()
    }

    fun loadFragment(frag: Int){
        var addToBackStack = false
        val newFrag = when(frag){
            0 -> MainHomeFragment.newInstance()
            1 -> MainInfoFragment.newInstance()
            2 -> MainAboutFragment.newInstance()
            else -> {
                addToBackStack = true
                MainNewProjectFragment.newInstance()
            }
        }
        val trans = supportFragmentManager.beginTransaction()
            .replace(ui_content.id, newFrag, newFrag.tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(addToBackStack) trans.addToBackStack(null)
        trans.commit()
    }
}
