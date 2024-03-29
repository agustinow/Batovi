package com.poronga.batovi.view.ui.project.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.poronga.batovi.*
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Task
import com.poronga.batovi.view.adapter.TaskAdapter
import com.poronga.batovi.view.adapter.TaskAdapterDetails
import com.poronga.batovi.view.ui.main.fragments.MainCreateProject
import com.poronga.batovi.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.fragment_project_tasks.*
import javax.inject.Inject


class ProjectTasksFragment : Fragment() {
    lateinit var model: ProjectViewModel
    lateinit var adapter: TaskAdapter
    @Inject
    lateinit var userManager: com.poronga.batovi.services.UserManager

    lateinit var tracker: SelectionTracker<Long>
    var selection: Selection<Long>? = null


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
        App.injector.inject(this@ProjectTasksFragment)
        adapter = TaskAdapter(context!!) { task, checked ->
            if (userManager.changeTaskCompletedStatus(model.project!!, task, checked)) userManager.saveProjects()
            else Toast.makeText(context!!, "Unexpected error", Toast.LENGTH_SHORT).show()
            val taskCompleted = (model.project!!.tasks)!!.filter {
                it.completed
            }.size

            when{
                (taskCompleted==5)->{userManager.giveAchievementent(ACHIV_TASK1)}
                (taskCompleted==15)->{userManager.giveAchievementent(ACHIV_TASK2)}
            }
        }

        loadTasks()
        swipe_layout.setColorSchemeColors(resources.getColor(R.color.colorPrimary,resources.newTheme()),resources.getColor(R.color.colorBackground,resources.newTheme()))
        swipe_layout.setOnRefreshListener {
            //live data pija observe
            loadTasks()

        }
        btnNewTask.setOnClickListener {
            createTask()
        }

        btnReleaseProject.setOnClickListener {
            if (!model.project!!.completed){
                btnReleaseProject.text="Release Project"
                model.project!!.completed=true
                val completedProject=App.projects.filter {
                    it.completed
                }.size
                when{
                    (completedProject>5)->{
                        userManager.giveAchievementent(ACHIV_PROJECT1)
                    }
                    (completedProject>10)->{
                        userManager.giveAchievementent(ACHIV_PROJECT2)
                    }
                    (completedProject>15)->{
                        userManager.giveAchievementent(ACHIV_PROJECT3)
                    }
                }
            }else{
                btnReleaseProject.text="Resume Project"
                model.project!!.completed=false
            }
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
                project_tasks_recycler.visibility = View.VISIBLE
                project_tasks_recycler.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

                val decor = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
                project_tasks_recycler.addItemDecoration(decor)
                adapter.resetTasks(this)

                tracker = SelectionTracker.Builder<Long>(
                    "selection",
                    project_tasks_recycler,
                    StableIdKeyProvider(project_tasks_recycler),
                    TaskAdapterDetails(project_tasks_recycler),
                    StorageStrategy.createLongStorage()
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()

                tracker.addObserver(object: SelectionTracker.SelectionObserver<Long>(){
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        selection = tracker.selection
                        when(selection?.size()){
                            0 ->{
                                noSelected()
                            }
                            1 ->{
                                oneSelected()
                            }
                            else -> {
                                nSelected()
                            }
                        }
                    }
                })

                adapter.tracker = tracker
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
                    when{
                        (model.project!!.tasks!!.size==5)->{
                            userManager.giveAchievementent(ACHIV_LATERMOM)
                        }
                        (model.project!!.tasks!!.size==20)->{
                            userManager.giveAchievementent(ACHIV_IMPOSSIBLE)
                        }
                    }
                }
            }
            findViewById<ImageButton>(R.id.imgCleanForm).setOnClickListener {
                name.text!!.clear()
                description.text!!.clear()

            }
            show()
        }
    }

    fun noSelected(){
        /*
        activity!!.more_options.visibility = View.GONE
        activity!!.more_options.setOnClickListener {  }
        */
    }

    fun oneSelected(){
        /*
        activity!!.more_options.visibility = View.VISIBLE
        activity!!.more_options.setOnClickListener {
            myMaterialDialog.show{
                listItems(R.array.one_task_selected) { _, index, _ ->
                    val list = selection?.map {
                        adapter.tasks[it.toInt()]
                    }?.toList()
                    when(index){
                        0 -> {
                            //EDIT

                        }
                        else -> {
                            userManager.removeTask(model.project!!.name, list!![0].name!!)
                            userManager.saveProjects()
                            val list = selection?.map {
                                adapter.tasks[it.toInt()]
                            }?.toList()
                            userManager.removeTask(model.project!!.name, list!![0].name!!)

                            userManager.saveProjects()
                            loadTasks()

                            Toast.makeText(context, "Meme", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

         */
    }

    fun nSelected(){
        /*
        activity!!.more_options.visibility = View.VISIBLE
        activity!!.more_options.setOnClickListener {
            myMaterialDialog.show{
                listItems(R.array.n_tasks_selected) { _, _, _ ->
                    val list = selection?.map {
                        adapter.tasks[it.toInt()]
                    }?.toList()
                    for (element in list!!){
                        userManager.removeTask(model.project!!.name, element.name!!)
                    }

                    userManager.saveProjects()
                    loadTasks()

                    Toast.makeText(context, "Meme", Toast.LENGTH_SHORT).show()
                }
            }
        }

         */
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectTasksFragment()
    }
}
