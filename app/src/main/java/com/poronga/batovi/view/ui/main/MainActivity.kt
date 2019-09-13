package com.poronga.batovi.view.ui.main

import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.ui.main.fragments.*
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_top_menu.view.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var userManager: UserManager

    lateinit var drawer: Drawer
    lateinit var drawerHeader: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //LOAD VIEW MODEL
        model = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]
        App.projects = sampleProjects.toMutableList()
        //INJECT
        App.injector.inject(this@MainActivity)
        userManager.onXPChanged = {
            resetProgressBar()
            if(it){
                Snackbar.make(main_layout, "Level up!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.colorPrimary))
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }
        userManager.onAchievementGiven = {
            Snackbar.make(main_layout, "Achievement $it unlocked!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .setTextColor(Color.WHITE)
                .show()
        }
        //CHECK IF USER IS FOUND, IF NOT, ASK.
        if(App.currentUser == null) {
            newUser()
        } else{
            onUserExists()
        }

    }
    fun newUser(){
        MaterialDialog(this@MainActivity, MaterialDialog.DEFAULT_BEHAVIOR).show{
            input(hint = "What's your name?", maxLength = 20, allowEmpty = false) { dialog, text ->
                userManager.createUser(text.toString(), null)
                App.currentUser = userManager.getUser()!!
                App.projects = sampleProjects.toMutableList()
                userManager.saveProjects()
                onUserExists()
                dialog.dismiss()
            }
        }
    }

    fun askDifficulty(){
        val dialog = Dialog(this@MainActivity)
        with(dialog) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
                newProjectDialog(DIFF_PRO)
                dismiss()
            }
            show()
        }
    }

    fun newProjectDialog(difficulty: Int){
        model.newProjectDifficulty = difficulty
        loadFragment(5)
    }

    fun onUserExists(){
        //LOAD SAMPLE DATA
        loadDrawer()
        loadFragment(model.selectedFrag)
    }

    fun loadDrawer(){
        val itemHome = PrimaryDrawerItem().withIdentifier(0).withName("Home")
        val itemAbout = PrimaryDrawerItem().withIdentifier(1).withName("Info")
        val itemInfo = PrimaryDrawerItem().withIdentifier(2).withName("About")
        val itemAchievements= PrimaryDrawerItem().withIdentifier(2).withName("Achievements")
        val itemAdd = SecondaryDrawerItem().withIdentifier(3).withName("New Project")

        drawerHeader = LayoutInflater.from(this).inflate(R.layout.drawer_top_menu, null, false)
        resetProgressBar()
        Glide.with(this@MainActivity).load(App.currentUser!!.image).into(drawerHeader.btn_user_img)
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
            5 -> MainCreateProject.newInstance()
            else -> {
                addToBackStack = true
                MainNewProjectFragment.newInstance()
            }
        }
        model.selectedFrag = frag
        if(frag == 5){
            (newFrag as MainCreateProject).show(supportFragmentManager, newFrag.tag)
            return@loadFragment
        }
        val trans = supportFragmentManager.beginTransaction()
            .replace(ui_content.id, newFrag, newFrag.tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(addToBackStack) trans.addToBackStack(null)
        trans.commit()
    }

    fun resetProgressBar(){
        val usr = App.currentUser!!
        drawerHeader.txt_user_name.text = usr.name
        drawerHeader.txt_level.text = "Level ${usr.lvl}"
        drawerHeader.txt_user_id.text = "#${usr.id}"
        drawerHeader.txt_user_xp.text = "${usr.xp} xp"
        drawerHeader.progress_user_xp.progress = usr.xp
    }
}