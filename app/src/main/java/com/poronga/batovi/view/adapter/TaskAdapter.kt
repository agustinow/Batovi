package com.poronga.batovi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.CompoundButton
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Task
import kotlinx.android.synthetic.main.element_task.view.*

class TaskAdapter(val context: Context, val onCheckedChange: (Task, Boolean) -> (Unit)): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var tasks: MutableList<Task> = mutableListOf()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //Felipe o agustin del futuro pls agregar la opcion de seleccionar varios items de mierda jua jua.
    val differ:AsyncListDiffer<Task> = AsyncListDiffer(this@TaskAdapter,object :DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return(oldItem.name==newItem.name)
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return(oldItem.name==newItem.name)

        }

    })

    /*
    private val arrowToUp = RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        interpolator = DecelerateInterpolator()
        repeatCount = 0
        duration = 150
        fillAfter = true
    }

    private val arrowToDown = RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        interpolator = DecelerateInterpolator()
        repeatCount = 0
        duration = 150
        fillAfter = true
    }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.element_task,parent,false))

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[holder.adapterPosition]
        tracker?.let {
            holder.bind(task, it.isSelected(holder.adapterPosition.toLong()))
        }

    }

    fun resetTasks(taskList: MutableList<Task>){
        tasks = taskList
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.element_task_selected_layout
        val name = itemView.element_task_name
        val expander = itemView.element_task_button
        val innerLayout = itemView.element_task_inner_content
        val description = itemView.element_task_info_description
        val difficulty = itemView.element_task_difficulty
        val guideline = itemView.element_task_guideline
        val completed = itemView.element_task_completed

        fun bind(task: Task, isActivated: Boolean){
            name.text = if(task.completed) "${task.name} (completed)"
            else task.name
            description.text = task.description
            difficulty.text = when(task.difficulty){
                1 -> {
                    difficulty.setTextColor(context.getColor(R.color.md_green_400))
                    "Easy"
                }
                2 -> {
                    "Normal"
                }
                else -> {
                    difficulty.setTextColor(context.getColor(R.color.md_red_400))
                    "Hard"
                }
            }
            completed.isChecked = task.completed
            val onLayoutClick = {
                if(innerLayout.visibility == View.GONE){
                    innerLayout.visibility = View.VISIBLE
                    expander.startAnimation(
                        RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                            interpolator = DecelerateInterpolator()
                            repeatCount = 0
                            duration = 150
                            fillAfter = true
                        }
                    )
                } else {
                    innerLayout.visibility = View.GONE
                    expander.startAnimation(RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                        interpolator = DecelerateInterpolator()
                        repeatCount = 0
                        duration = 150
                        fillAfter = true
                    })
                }
            }
            completed.setOnCheckedChangeListener { _, isChecked -> onCheckedChange(task, isChecked) }
            expander.setOnClickListener { onLayoutClick() }
            layout.setOnClickListener { onLayoutClick() }
            //layout.setOnLongClickListener {
            //    isActivated = !isActivated
            //    false
            //}
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object: ItemDetailsLookup.ItemDetails<Long>(){
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }
}