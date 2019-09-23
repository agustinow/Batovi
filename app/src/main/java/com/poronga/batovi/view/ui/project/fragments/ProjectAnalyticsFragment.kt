package com.poronga.batovi.view.ui.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.poronga.batovi.R
import com.poronga.batovi.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project_analytics.*
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.poronga.batovi.colors
import kotlinx.android.synthetic.main.fragment_project_analytics.chart
import java.util.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.components.AxisBase


class ProjectAnalyticsFragment : Fragment() {
    lateinit var model: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[ProjectViewModel::class.java]
        txtProjectTasks.text= "Total Tasks: "+model.project!!.tasks!!.size.toString()
        val tasksCompleted=model.project!!.tasks!!.filter {
            it.completed
        }.size
        val taskWip=(model.project!!.tasks!!.size-tasksCompleted)
        txtCompletedTasks.text="Tasks Completed: $tasksCompleted"
        txtWipTasks.text="WIP Tasks: $taskWip"
        loadChart()

    }


    fun loadChart(){
        val entries = ArrayList<BarEntry>()
        val texts= listOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
        val formatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return texts[value.toInt()]
            }
        }
        val xAxis = chart.xAxis
        xAxis.valueFormatter=formatter
        val tpw = model.project!!.tasksPerWeek()
        with(entries){
            add(BarEntry(0f, tpw[0].toFloat()))
            add(BarEntry(1f, tpw[1].toFloat()))
            add(BarEntry(2f, tpw[2].toFloat()))
            add(BarEntry(3f, tpw[3].toFloat()))
            add(BarEntry(4f, tpw[4].toFloat()))
            add(BarEntry(5f, tpw[5].toFloat()))
            add(BarEntry(6f, tpw[6].toFloat()))
        }
        val set = BarDataSet(entries,"BarData")
        val data = BarData(set)
        data.barWidth=0.5f
        set.colors = colors.shuffled(Random())
        chart.data=data
        with(chart){
            description.text=""
            setFitBars(true)
            legend.isEnabled = false
            setDrawValueAboveBar(true)
            animateY(1500, Easing.EaseInOutBack)
            invalidate()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProjectAnalyticsFragment()
    }
}
