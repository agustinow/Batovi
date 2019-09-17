package com.poronga.batovi.view.ui.project.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.poronga.batovi.R
import com.poronga.batovi.viewmodel.project.ProjectViewModel

class ProjectAnalyticsFragment : Fragment() {
    lateinit var model: ProjectViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[ProjectViewModel::class.java]

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectAnalyticsFragment()
    }
}
