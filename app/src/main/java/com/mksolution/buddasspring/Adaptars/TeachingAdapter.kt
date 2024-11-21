package com.mksolution.buddasspring.Adaptars

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mksolution.buddasspring.Models.Teaching
import com.mksolution.buddasspring.R
import com.mksolution.buddasspring.ReadStoriesActivity
import com.mksolution.buddasspring.ReadTeachingActivity

class TeachingAdapter(var context: Context,val list: ArrayList<Teaching>) :RecyclerView.Adapter<TeachingAdapter.TeachingHolder>(){
    private val colors = listOf(
        R.color.grad3,
        R.color.grad4,
        R.color.gray2,
        R.color.raund1,
        R.color.blogb,
        R.color.mainBack,
        R.color.timeBack,
        R.color.redBack,
        R.color.breathcolor2,
        R.color.raund2
    )
    inner class TeachingHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val titleTe = itemView.findViewById<TextView>(R.id.techingTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeachingHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.teaching_item,parent,false)
        return TeachingHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeachingHolder, position: Int) {
        val data = list[position]
        if (data.teachTitle.isNotEmpty()){
            holder.titleTe.text = data.teachTitle
        }
        val color = colors[position % colors.size]
        holder.itemView.setBackgroundResource(color)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReadTeachingActivity::class.java)
            intent.putExtra("TEACHING_T",data.teachTitle)
            intent.putExtra("TEACHING_DES",data.teachDes)
            intent.putExtra("TEACHING_URL",data.ImageUrl)

            context.startActivity(intent)
        }

    }
}