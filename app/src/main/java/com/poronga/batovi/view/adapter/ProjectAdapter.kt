package com.poronga.batovi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.poronga.batovi.App
import com.poronga.batovi.R
import com.poronga.batovi.formatDate
import com.poronga.batovi.model.json.Project

class ProjectAdapter(val context: Context, val onClick: (Project) -> (Unit)): RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    private var projects: MutableList<Project> = mutableListOf()
    var type=1
    val differ: AsyncListDiffer<Project> = AsyncListDiffer(this@ProjectAdapter, object: DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return(oldItem.name == newItem.name)
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return(oldItem.description == newItem.description && oldItem.dateCreated == newItem.dateCreated)
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType == 1) ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_project,parent,false))
        else ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_recent_project,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return if(type==1) 1
        else 0
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = differ.currentList[holder.adapterPosition]

        val drawable = when(project.difficulty){
            1 -> {
                context.getDrawable(R.drawable.ic_coffe_cup2)
            }
            2 -> {
                context.getDrawable(R.drawable.ic_coffe_cup1)
            }
            3 -> {
                context.getDrawable(R.drawable.ic_coffe_ma3)
            }
            4 -> {
                context.getDrawable(R.drawable.ic_coffe_ma1)
            }
            else -> {
                context.getDrawable(R.drawable.ic_coffe_cup2)
            }
        }
        holder.img.setImageDrawable(drawable)
        holder.name.text = project.name
        holder.date.text = formatDate(project.dateFinish!!)
        holder.layout.setOnClickListener {
            onClick(project)
        }
    }

    fun setItems(list: MutableList<Project>){
        this.projects = list
        differ.submitList(projects)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ConstraintLayout>(R.id.element_project)
        val img = itemView.findViewById<ImageView>(R.id.imgProject)!!
        val name = itemView.findViewById<TextView>(R.id.txtProjectNameLayout)!!
        val date = itemView.findViewById<TextView>(R.id.txtProjectDate)!!
    }
}