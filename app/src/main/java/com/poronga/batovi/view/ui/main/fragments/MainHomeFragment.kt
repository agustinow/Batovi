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

    fun onUserExists(){
        adapter = ProjectAdapter(context!!) {
            //Intent
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(EXTRA_PROJECT_NAME, it.name)
            startActivity(intent)
        }
        val lm = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        val decor = DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL)
        adapter.setItems(App.projects)
        recycler_projects.layoutManager = lm
        recycler_projects.addItemDecoration(decor)
        recycler_projects.adapter = adapter
        loadChart()
    }

    fun loadChart(){
        val entries = ArrayList<PieEntry>()
        val set = PieDataSet(entries,"")
        val data = PieData(set)
        with(entries){
            add(PieEntry(12.3f, "Portfolio"))
            add(PieEntry(46.5f, "S.G.Ven"))
            add(PieEntry(20.8f, "This"))
            add(PieEntry(14.2f, "Doing nothing"))
            add(PieEntry(6.2f, "Masturbating"))
        }
        chart.data = data
        with(chart){
            description.text=""
            legend.isEnabled = false
            isDrawHoleEnabled = false
            animateY(1500, Easing.EaseInOutBack)
        }
        set.colors = colors.shuffled(Random())

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainHomeFragment()
    }
}
