package com.poronga.batovi.view.ui.main

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
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
import com.poronga.batovi.view.ui.main.fragments.*
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_top_menu.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    @Inject
    lateinit var gson: Gson
    lateinit var drawer: Drawer
    lateinit var drawerHeader: View

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
        val user = User(id = 1, name = name, xp = 0, achievements = mutableListOf())
        val userJson = gson.toJson(user)
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PREF_USER, userJson)
            .apply()
    }

    fun saveUser(){
        val userJson = gson.toJson(App.currentUser)
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
        loadFragment(4)
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
        loadFragment(1)
    }

    fun loadDrawer(){
        val itemHome = PrimaryDrawerItem().withIdentifier(0).withName("Home")
        val itemAbout = PrimaryDrawerItem().withIdentifier(1).withName("Info")
        val itemInfo = PrimaryDrawerItem().withIdentifier(2).withName("About")
        val itemAchievements= PrimaryDrawerItem().withIdentifier(2).withName("Achievements")
        val itemAdd = SecondaryDrawerItem().withIdentifier(3).withName("New Project")

        drawerHeader = LayoutInflater.from(this).inflate(R.layout.drawer_top_menu, null, false)
        resetProgressBar()
        Glide.with(this@MainActivity).load(App.currentUser.image).into(drawerHeader.btn_user_img)
            //ONCLICK

        drawer = DrawerBuilder()
            .withActivity(this@MainActivity)
            .addDrawerItems(
                itemHome,
                itemAbout,
                itemInfo,
                itemAchievements,
                itemAdd
            )
            .withHeader(drawerHeader)
            .withToolbar(toolbar as Toolbar)
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    //DO STUFF
                    if(position == 5) askDifficulty()
                    else loadFragment(position)
                return false
                }
            })
            .build()


    }

    fun loadFragment(frag: Int){
        var addToBackStack = false
        val newFrag = when(frag){
            1 -> MainHomeFragment.newInstance()
            2 -> MainInfoFragment.newInstance()
            3 -> MainAboutFragment.newInstance()
            4 -> MainAchievementFragment.newInstance()
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

    fun giveAchievementent(id:Int){
        if(!App.currentUser.achievements.contains(id)){
            giveXp(model.achievements[id].xp)
            App.currentUser.achievements.add(id)
            saveUser()
        }

    }

    fun giveXp(value:Int){
        App.currentUser.xp += value
        if(App.currentUser.xp >= 100){
            App.currentUser.lvl++
            App.currentUser.xp -= 100
        }
        resetProgressBar()
    }

    fun resetProgressBar(){
        val usr = App.currentUser
        drawerHeader.txt_user_name.text = usr.name
        drawerHeader.txt_level.text = "Level ${usr.lvl}"
        drawerHeader.txt_user_id.text = "#${usr.id}"
        drawerHeader.txt_user_xp.text = "${usr.xp} xp"
        drawerHeader.progress_user_xp.progress = usr.xp
    }

}