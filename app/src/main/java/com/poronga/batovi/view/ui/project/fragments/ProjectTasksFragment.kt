package com.poronga.batovi.view.ui.project.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.poronga.batovi.R
import com.poronga.batovi.view.adapter.TaskAdapter
import com.poronga.batovi.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project_tasks.*

class ProjectTasksFragment : Fragment() {
    lateinit var model: ProjectViewModel
    lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!)[ProjectViewModel::class.java]
        adapter = TaskAdapter(context!!) {
            Toast.makeText(context!!, "Task ${it.name} selected", Toast.LENGTH_SHORT).show()
        }
        with(model.project!!.tasks!!){
            if(this.isEmpty()){
                project_tasks_recycler.visibility = View.GONE
                Toast.makeText(context!!, "Add some tasks!", Toast.LENGTH_SHORT).show()
            } else {
                project_tasks_recycler.adapter = adapter
                project_tasks_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                val decor = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
                project_tasks_recycler.addItemDecoration(decor)
                adapter.setTasks(this)
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectTasksFragment()
    }
}
