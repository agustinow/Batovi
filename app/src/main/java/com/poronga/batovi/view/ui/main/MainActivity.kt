package com.poronga.batovi.view.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItems
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.poronga.batovi.*
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.ui.main.fragments.*
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.drawer_top_menu.view.*
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
        //INJECT
        App.injector.inject(this@MainActivity)
        App.projects = userManager.getProjects()!!
        App.recentProjects= mutableListOf()
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
        userManager.onProjectCreated={
            Snackbar.make(main_layout, "${it.name} Created!", Snackbar.LENGTH_SHORT)
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





    fun onUserExists(){
        //LOAD SAMPLE DATA
        loadDrawer()
        loadFragment(model.selectedFrag)
    }

    fun loadDrawer(){
        val itemHome = PrimaryDrawerItem().withIdentifier(0).withName("Home")
            itemHome.icon= ImageHolder(resources.getDrawable(R.drawable.ic_home,resources.newTheme()))
        val itemAbout = PrimaryDrawerItem().withIdentifier(1).withName("Info")
            itemAbout.icon= ImageHolder(resources.getDrawable(R.drawable.ic_chart,resources.newTheme()))
        val itemInfo = PrimaryDrawerItem().withIdentifier(2).withName("About")
            itemInfo.icon= ImageHolder(resources.getDrawable(R.drawable.ico_info,resources.newTheme()))
        val itemAchievements= PrimaryDrawerItem().withIdentifier(3).withName("Achievements")
            itemAchievements.icon= ImageHolder(resources.getDrawable(R.drawable.ic_trofe,resources.newTheme()))
        val itemAdd = SecondaryDrawerItem().withIdentifier(4).withName("New Project")
            itemAdd.icon= ImageHolder(resources.getDrawable(R.drawable.ic_add,resources.newTheme()))
        val itemSampleData = SecondaryDrawerItem().withIdentifier(5).withName("Load Sample Data")

        drawerHeader = LayoutInflater.from(this).inflate(R.layout.drawer_top_menu, null, false)
        drawerHeader.btn_user_img.setOnClickListener {
            this@MainActivity.showChangeImageDialog()
        }

        drawer = DrawerBuilder()
            .withActivity(this@MainActivity)
            .addDrawerItems(
                itemHome,
                itemAbout,
                itemInfo,
                itemAchievements,
                itemAdd,
                itemSampleData
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
                    if(position == 6) {
                        App.projects = sampleProjects.toMutableList()
                        userManager.saveProjects()
                    } else loadFragment(position)
                    return false
                }
            })
            .build()

        resetProgressBar()
    }

    fun loadFragment(frag: Int){
        val newFrag = when(frag){
            FRAG_HOME -> MainHomeFragment.newInstance() //1
            FRAG_INFO -> MainInfoFragment.newInstance() //2
            FRAG_ABOUT -> MainAboutFragment.newInstance() //3
            FRAG_ACHIEVEMENT -> MainAchievementFragment.newInstance() //4
            FRAG_CREATE_PROJECT -> MainCreateProject.newInstance() //5
            else -> MainHomeFragment.newInstance()
        }
        if(frag == FRAG_CREATE_PROJECT){
            (newFrag as MainCreateProject).show(supportFragmentManager, newFrag.tag)
            return
        } else {
            model.selectedFrag = frag
        }
        with(supportFragmentManager.beginTransaction()) {
            replace(ui_content.id, newFrag, newFrag.tag)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }

    fun resetProgressBar(){
        val usr = App.currentUser!!
        drawer.header!!.txt_user_name.text = usr.name
        drawer.header!!.txt_level.text = "Level ${usr.lvl}"
        drawer.header!!.txt_user_id.text = "#${usr.id}"
        drawer.header!!.txt_user_xp.text = "${usr.xp} xp"
        drawer.header!!.progress_user_xp.progress = usr.xp
        Glide.with(this@MainActivity).load(App.currentUser!!.image).placeholder(R.drawable.ic_heart1).into(drawer.header!!.btn_user_img)
    }

    fun showChangeImageDialog() {
        MaterialDialog(this@MainActivity, MaterialDialog.DEFAULT_BEHAVIOR).show{
            listItems(R.array.image_select_array) { _, index, _ ->
                when(index){
                    0 -> {
                        this@MainActivity.chooseFromGallery()
                        dismiss()
                    }
                    else -> {
                        dismiss()
                    }
                }
            }
        }
    }

    fun chooseFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val types = listOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types.toTypedArray())
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onBackPressed() {
        loadFragment(FRAG_HOME)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                GALLERY_REQUEST_CODE -> {
                    val image = data!!.data
                    App.currentUser!!.image = image.toString()
                    userManager.saveUser()
                    resetProgressBar()
                    //drawerHeader.btn_user_img.setImageURI(image)
                }
            }
        }
    }
}