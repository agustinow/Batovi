package com.poronga.batovi.view.ui.main.fragments


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.shape.ShapeAppearanceModel
import com.poronga.batovi.*
import com.poronga.batovi.view.adapter.ProjectAdapter
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.view.ui.project.ProjectActivity
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_home.*
import java.util.*
import kotlin.collections.ArrayList

class MainHomeFragment : Fragment() {
    lateinit var model: MainViewModel
    lateinit var adapter: ProjectAdapter
    lateinit var recentProjectAdapter: ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[MainViewModel::class.java]
        btnNewProject.setOnClickListener {
            (activity as MainActivity).loadFragment(FRAG_CREATE_PROJECT)
        }
        onUserExists()

    }

    override fun onResume() {
        super.onResume()
        displayRecent()
        onUserExists()
    }

    fun onUserExists(){
        adapter = ProjectAdapter(context!!) {
            //Intent
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(EXTRA_PROJECT_NAME, it.name)
            startActivity(intent)
            model.projecto = it
            App.recentProjects = (listOf(it) + App.recentProjects).toMutableList()
        }
        val lm = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        val decor = DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL)
        adapter.type=1
        adapter.setItems(App.projects)
        recycler_projects.layoutManager = lm
        recycler_projects.addItemDecoration(decor)
        recycler_projects.adapter = adapter

        displayRecent()
    }
    fun displayRecent(){
        recentProjectAdapter = ProjectAdapter(context!!) {
            //Intent
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(EXTRA_PROJECT_NAME, it.name)
            startActivity(intent)
        }
        val lm = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, true)
        val decor = DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL)
        App.recentProjects = App.recentProjects.toSet().toMutableList()
        if(App.recentProjects.size > 3) App.recentProjects = App.recentProjects.subList(0, 3)
        recentProjectAdapter.type=0
        recentProjectAdapter.setItems(App.recentProjects.toSet().toMutableList())
        lm.isSmoothScrollbarEnabled=true
        recycler_recent_projects.layoutManager = lm
        recycler_recent_projects.addItemDecoration(decor)
        recycler_recent_projects.adapter = recentProjectAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainHomeFragment()
    }
}
