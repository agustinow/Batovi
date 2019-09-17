package com.poronga.batovi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.RecyclerView
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Task
import kotlinx.android.synthetic.main.element_task.view.*

class TaskAdapter(val context: Context, val onClickAction: (Task) -> (Unit)): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var tasks: MutableList<Task> = mutableListOf()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.element_task, parent, false))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[holder.adapterPosition]

        with(holder){
            name.text = task.name
            description.text = task.description
            difficulty.text = when(task.difficulty){
                1 -> "Easy"
                2 -> "Normal"
                else -> "Hard"
            }
            flag.setImageDrawable(
                if(task.completed) context.getDrawable(R.drawable.gold)
                else context.getDrawable(R.drawable.bronze)
            )
            layout.setOnClickListener {
                if(innerLayout.visibility == View.GONE){
                    innerLayout.visibility = View.VISIBLE
                    expander.startAnimation(arrowToUp)
                } else {
                    innerLayout.visibility = View.GONE
                    expander.startAnimation(arrowToDown)
                }
            }

            layout.setOnLongClickListener {
                onClickAction(task)
                true
            }
        }

    }

    fun setTasks(taskList: MutableList<Task>){
        tasks = taskList
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.element_task_layout
        val name = itemView.element_task_name
        val expander = itemView.element_task_button
        val flag = itemView.element_task_temporal_flag
        val innerLayout = itemView.element_task_inner_content
        val description = itemView.element_task_info_description
        val difficulty = itemView.element_task_difficulty
        val guideline = itemView.element_task_guideline
    }
}