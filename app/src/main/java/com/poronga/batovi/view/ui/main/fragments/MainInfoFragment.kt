package com.poronga.batovi.view.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.poronga.batovi.App
import com.poronga.batovi.R
import com.poronga.batovi.colors
import kotlinx.android.synthetic.main.fragment_main_home.*


import kotlinx.android.synthetic.main.fragment_main_info.*
import kotlinx.android.synthetic.main.fragment_main_info.chart
import java.util.*
import kotlin.collections.ArrayList

class MainInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadChart()
        txtTotalProjects.text ="Total Projects: "+ App.projects.size.toString()
    }


    fun loadChart(){
        val entries = ArrayList<PieEntry>()
        val set = PieDataSet(entries,"")
        val data = PieData(set)
        with(entries){
            add(PieEntry(12.3f, "Finished"))
            add(PieEntry(46.5f, "WIP"))
            add(PieEntry(20.8f, "Canceled"))
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
        fun newInstance() = MainInfoFragment()
    }
}
