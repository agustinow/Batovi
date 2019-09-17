package com.poronga.batovi.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poronga.batovi.view.ui.project.fragments.ProjectAnalyticsFragment
import com.poronga.batovi.view.ui.project.fragments.ProjectTasksFragment

class ProjectFragmentsAdapter(lifecycle: Lifecycle, fm: FragmentManager): FragmentStateAdapter(fm, lifecycle) {
    val frags = listOf(
        ProjectTasksFragment.newInstance(),
        ProjectAnalyticsFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return frags.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> frags[0]
            else -> frags[1]
        }
    }
}