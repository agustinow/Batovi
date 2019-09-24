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

    }


    fun loadChart(){
        val entries = ArrayList<PieEntry>()
        val set = PieDataSet(entries,"piedataset")
        val data = PieData(set)
        val weekDays = mutableListOf(0, 0, 0, 0, 0, 0, 0)
        var completed = 0
        var totalTasks = 0
        for(project in App.projects){
            totalTasks += project.tasks!!.size
            val tpw = project.tasksPerWeek()
            for(i in 0..6){
                weekDays[i] += tpw[i]
            }
            if(project.completed) completed++
        }
        var totalFinished = 0
        for(dayTasks in weekDays){
            totalFinished += dayTasks
        }

        txtTotalProjects.text = "Total Projects: "+ App.projects.size.toString()
        txtPercentageProjects.text = "${(completed*100)/(App.projects.size)}% completed"

        txtTotalTasks.text = "Total Tasks: $totalTasks"
        txtPercentageTasks.text = "${(totalFinished*100)/(totalTasks)}% completed"

        with(entries){
            if(weekDays[0] > 0) add(PieEntry(weekDays[0].toFloat(), "Mondays"))
            if(weekDays[1] > 0) add(PieEntry(weekDays[1].toFloat(), "Tuesdays"))
            if(weekDays[2] > 0) add(PieEntry(weekDays[2].toFloat(), "Wednesdays"))
            if(weekDays[3] > 0) add(PieEntry(weekDays[3].toFloat(), "Thursdays"))
            if(weekDays[4] > 0) add(PieEntry(weekDays[4].toFloat(), "Fridays"))
            if(weekDays[5] > 0) add(PieEntry(weekDays[5].toFloat(), "Saturdays"))
            if(weekDays[6] > 0) add(PieEntry(weekDays[6].toFloat(), "Sundays"))
        }
        chart.data = data
        with(chart){
            description.text=""
            legend.isEnabled = false
            isDrawHoleEnabled = false
            animateY(1500, Easing.EaseInOutBack)
            setUsePercentValues(true)
        }
        set.colors = colors.shuffled(Random())

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainInfoFragment()
    }
}
