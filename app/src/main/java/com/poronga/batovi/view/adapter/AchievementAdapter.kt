package com.poronga.batovi.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
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
import com.poronga.batovi.model.json.Achievement

class AchievementAdapter(val context: Context, val onClick: (Achievement) -> (Unit)):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var achievements: MutableList<Achievement> = mutableListOf()
    val differ:AsyncListDiffer<Achievement> = AsyncListDiffer(this@AchievementAdapter,object :DiffUtil.ItemCallback<Achievement>(){
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return(oldItem.id==newItem.id)
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return(oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.difficulty==newItem.difficulty && oldItem.xp == newItem.xp)
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return if(viewType == 1) ViewHolderUnlocked(LayoutInflater.from(context).inflate(R.layout.element_achievement,parent,false))
        else ViewHolderLocked(LayoutInflater.from(context).inflate(R.layout.element_achievement_gray,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return if(App.currentUser.achievements.contains(differ.currentList[position].id)) 1
        else 0
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val achievement = differ.currentList[holder.adapterPosition]

        if(holder is ViewHolderLocked) {
            with(holder){
                name.text=achievement.name
                description.text=achievement.description
                holder.image.background = context.getDrawable(achievement.imageID!!)
                xp.text = "+${achievement.xp}xp"
                layout.setOnClickListener {
                    onClick(achievement)
                    notifyDataSetChanged()
                }

            }

        }else{
            with(holder as ViewHolderUnlocked){
                name.text=achievement.name
                description.text=achievement.description
                holder.image.background = context.getDrawable(achievement.imageID!!)
                xp.text = "+${achievement.xp}xp"
                layout.setOnClickListener {
                    onClick(achievement)
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun setItems(list:MutableList<Achievement>){
        this.achievements=list
        differ.submitList(achievements)
        notifyDataSetChanged()
    }

    inner class ViewHolderLocked(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ConstraintLayout>(R.id.element_achievement_gray)
        val name = itemView.findViewById<TextView>(R.id.txtAchNameGray)!!
        val description = itemView.findViewById<TextView>(R.id.txtAchDescriptionGray)!!
        val xp = itemView.findViewById<TextView>(R.id.txtAchXpGray)!!
        val image= itemView.findViewById<ImageView>(R.id.imgAchLogoGray)!!
    }

    inner class ViewHolderUnlocked(itemView: View): RecyclerView.ViewHolder(itemView){
        val layout = itemView.findViewById<ConstraintLayout>(R.id.element_achievement)
        val name = itemView.findViewById<TextView>(R.id.txtAchName)!!
        val description = itemView.findViewById<TextView>(R.id.txtAchDescription)!!
        val xp = itemView.findViewById<TextView>(R.id.txtAchXp)!!
         val image= itemView.findViewById<ImageView>(R.id.imgAchLogo)!!
    }
}