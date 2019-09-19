package com.poronga.batovi.view.ui.project.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.UserManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Task
import com.poronga.batovi.view.adapter.TaskAdapter
import com.poronga.batovi.view.ui.main.MainActivity
import com.poronga.batovi.view.ui.project.ProjectActivity
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
            model.project!!.tasks!!.remove(it)
            loadTasks()
            //(activity!! as ProjectActivity).userManager.giveAchievementent(4)
        }
        swipe_layout.isRefreshing=false
        loadTasks()
        swipe_layout.setColorSchemeColors(resources.getColor(R.color.colorPrimary,resources.newTheme()),resources.getColor(R.color.colorBackground,resources.newTheme()))
        swipe_layout.setOnRefreshListener {
            //live data pija observe
            loadTasks()
            swipe_layout.isRefreshing=true

        }
        btnNewTask.setOnClickListener {
            createTask()
        }
    }

    fun loadTasks(){
        with(model.project!!.tasks!!){
            if(this.isEmpty()){
                project_tasks_recycler.visibility = View.GONE
                txtNoTasks.visibility= View.VISIBLE

            } else {
                txtNoTasks.visibility= View.GONE
                project_tasks_recycler.adapter = adapter
                project_tasks_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                val decor = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
                project_tasks_recycler.addItemDecoration(decor)
                adapter.setTasks(this)
            }
        }
    }
    fun createTask(){
        val dialog = Dialog(context!!)
        with(dialog){
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
            setContentView(R.layout.dialog_newtask)
            val name = findViewById<TextInputEditText>(R.id.txtTaskNameNew)
            val nameLayout=findViewById<TextInputLayout>(R.id.txtTaskNameLayout)
            val description =findViewById<TextInputEditText>(R.id.txtTaskDescription)
            val descriptionLayout=findViewById<TextInputLayout>(R.id.txtTaskDescriptionLayout)
            findViewById<MaterialButton>(R.id.btnAddTask).setOnClickListener {
                var isSuccess=true
                nameLayout.error = if(name.text.isNullOrEmpty()){
                    isSuccess=false
                    "Name is empty!"
                } else null
                descriptionLayout.error = if(description.text.isNullOrEmpty()){
                    isSuccess=false
                    "Description is empty!"
                } else null
                if (isSuccess){
                    val actualTask=Task(
                        name = name.text.toString(),
                        description = description.text.toString(),
                        difficulty = 1,
                        completed = false
                    )
                    model.project!!.tasks!!.add(actualTask)
                    name.text!!.clear()
                    description.text!!.clear()
                    Toast.makeText(context,"Task Created",Toast.LENGTH_SHORT).show()
                    loadTasks()
                }
            }
            findViewById<ImageButton>(R.id.imgCleanForm).setOnClickListener {
                name.text!!.clear()
                description.text!!.clear()

            }
            show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectTasksFragment()
    }
}
