package com.mksolution.buddasspring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.mksolution.buddasspring.databinding.ActivityReadStoriesBinding

class ReadStoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadStoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("STORY_T").toString()
        val description = intent.getStringExtra("STORY_DES").toString()
        val imageUrl = intent.getStringExtra("STORY_URL").toString()
        if (imageUrl.isNotEmpty()){
            binding.imageReadStory.visibility = View.VISIBLE
            Glide.with(this).load(imageUrl).into(binding.imageReadStory)
        }

        binding.storyTitle.text = title

        binding.StoryDes.text = description

        binding.backReadS.setOnClickListener {
            finish()
        }
    }
}