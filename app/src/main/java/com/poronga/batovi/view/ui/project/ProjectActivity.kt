package com.poronga.batovi.view.ui.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.poronga.batovi.App
import com.poronga.batovi.EXTRA_PROJECT_NAME
import com.poronga.batovi.R
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
        toolbar.title = model.project!!.name



    }
}
