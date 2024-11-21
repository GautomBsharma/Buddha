package com.mksolution.buddasspring.Adaptars

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.mksolution.buddasspring.Models.Stories
import com.mksolution.buddasspring.R
import com.mksolution.buddasspring.ReadStoriesActivity

class StoryAdapter(var context: Context,var list: ArrayList<Stories>): RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    private val colors = listOf(
        R.color.grad3,
        R.color.grad4,
        R.color.gray2,
        R.color.raund1,
        R.color.blogb,
        R.color.mainBack,
        R.color.timeBack,
        R.color.breathcolor2,
        R.color.raund2
    )
    inner class StoryHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
       val title = itemView.findViewById<TextView>(R.id.titleStory)
       val readButton = itemView.findViewById<TextView>(R.id.button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.story_item,parent,false)
        return StoryHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val data = list[position]
        holder.title.text = data.storyTitle

        val color = colors[position % colors.size]
        holder.itemView.setBackgroundResource(color)

        holder.readButton.setOnClickListener {
            val intent = Intent(context,ReadStoriesActivity::class.java)
            intent.putExtra("STORY_T",data.storyTitle)
            intent.putExtra("STORY_DES",data.storyDes)
            intent.putExtra("STORY_URL",data.ImageUrl)
            context.startActivity(intent)

        }

    }
}