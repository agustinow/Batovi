package com.poronga.batovi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Project

class ProjectAdapter(val context: Context, val onClick: (Project) -> (Unit)): RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {
    val projects: MutableList<Project> = mutableListOf()
    val differ: AsyncListDiffer<Project> = AsyncListDiffer(this@ProjectAdapter, object: DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return(oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return(oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.dateCreated == newItem.dateCreated)
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_project, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = differ.currentList[holder.adapterPosition]

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}