package com.poronga.batovi.view.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.poronga.batovi.App

import com.poronga.batovi.R
import com.poronga.batovi.baseAchievements
import com.poronga.batovi.view.adapter.AchievementAdapter
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_achievement.*

class MainAchievementFragment : Fragment() {
    lateinit var model: MainViewModel
    lateinit var adapter: AchievementAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_achievement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[MainViewModel::class.java]

        adapter = AchievementAdapter(context!!){
            //click
            (activity!! as MainActivity).userManager.giveAchievementent(it.id!!)

        }
        val lm = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        val decor = DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL)
        adapter.setItems(baseAchievements.toMutableList())
        recyclerAchievement.layoutManager = lm
        recyclerAchievement.addItemDecoration(decor)
        recyclerAchievement.adapter = adapter

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainAchievementFragment()
    }
}
