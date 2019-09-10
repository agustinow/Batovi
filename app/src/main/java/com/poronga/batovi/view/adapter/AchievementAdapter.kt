package com.poronga.batovi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.poronga.batovi.R
import com.poronga.batovi.model.json.Achievement

class AchievementAdapter(val context: Context, val onClick: (Achievement) -> (Unit)):RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {
    private var achievements: MutableList<Achievement> = mutableListOf()
    val differ:AsyncListDiffer<Achievement> = AsyncListDiffer(this@AchievementAdapter,object :DiffUtil.ItemCallback<Achievement>(){
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return(oldItem.id==newItem.id)
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return(oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.difficulty==newItem.difficulty && oldItem.image == newItem.image && oldItem.xp == newItem.xp)
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.element_achievement,parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievements=differ.currentList[holder.adapterPosition]
        holder.name.text=achievements.name
        holder.layout.setOnClickListener {
            onClick(achievements)
        }

    }
    fun setItems(list:MutableList<Achievement>){
        this.achievements=list
        differ.submitList(achievements)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ConstraintLayout>(R.id.element_project_achievement)
        val name = itemView.findViewById<TextView>(R.id.txtAchName)!!

    }
}