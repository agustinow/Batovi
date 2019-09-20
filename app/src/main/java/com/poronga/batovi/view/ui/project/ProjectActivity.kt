package com.poronga.batovi.view.ui.project

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.poronga.batovi.App
import com.poronga.batovi.EXTRA_PROJECT_NAME
import com.poronga.batovi.R
import com.poronga.batovi.services.UserManager
import com.poronga.batovi.view.adapter.ProjectFragmentsAdapter
import com.poronga.batovi.view.ui.main.fragments.MainCreateProject
import com.poronga.batovi.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.activity_project.toolbar
import javax.inject.Inject

class ProjectActivity : AppCompatActivity() {
    lateinit var model: ProjectViewModel
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var userManager: UserManager

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_opt_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit_project -> {
                //MODIFY
                val dialog = MainCreateProject(model.project)
                dialog.show(supportFragmentManager, dialog.tag)
                true
            }
            R.id.delete_project -> {
                //DELETE
                App.projects.remove(model.project)
                userManager.saveProjects()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        setSupportActionBar(toolbar)
        model = ViewModelProviders.of(this@ProjectActivity)[ProjectViewModel::class.java]
        App.injector.inject(this@ProjectActivity)
        if(model.project == null) {
            val projectName = intent.getStringExtra(EXTRA_PROJECT_NAME)

            model.project = App.projects.find {
                it.name == projectName
            }
        }
        userManager.onXPChanged = {
            if(it){
                Snackbar.make(project_layout, "Level up!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.colorPrimary))
                    .setTextColor(Color.WHITE)
                    .show()
            }
        }
        userManager.onAchievementGiven = {
            Snackbar.make(project_layout, "Achievement $it unlocked!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .setTextColor(Color.WHITE)
                .show()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.title = model.project!!.name
        view_pager.adapter = ProjectFragmentsAdapter(lifecycle, supportFragmentManager)
        view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tabLayout, view_pager, true,
            TabLayoutMediator.OnConfigureTabCallback { tab, position ->
                when(position){
                    0 -> {
                     //   tab.text = "Tasks"
                        tab.icon=resources.getDrawable(R.drawable.calendar,resources.newTheme())

                    }
                    1 -> {
                      //  tab.text = "Analytics"
                        tab.icon=resources.getDrawable(R.drawable.ic_chart,resources.newTheme())
                    }
                }

            }).attach()

    }
}
