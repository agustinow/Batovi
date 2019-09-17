package com.poronga.batovi.view.ui.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.poronga.batovi.App
import com.poronga.batovi.EXTRA_PROJECT_NAME
import com.poronga.batovi.R
import com.poronga.batovi.view.adapter.ProjectFragmentsAdapter
import com.poronga.batovi.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.activity_project.*

class ProjectActivity : AppCompatActivity() {
    lateinit var model: ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        model = ViewModelProviders.of(this@ProjectActivity)[ProjectViewModel::class.java]

        if(model.project == null) {
            val projectName = intent.getStringExtra(EXTRA_PROJECT_NAME)

            model.project = App.projects.find {
                it.name == projectName
            }
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
                    0 -> tab.text = "Tasks"
                    1 -> tab.text = "Analytics"
                }
            }).attach()

    }
}
