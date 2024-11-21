package com.mksolution.buddasspring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.mksolution.buddasspring.databinding.ActivityReadTeachingBinding

class ReadTeachingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadTeachingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTeachingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra("TEACHING_T").toString()
        val description = intent.getStringExtra("TEACHING_DES").toString()
        val imageUrl = intent.getStringExtra("TEACHING_URL").toString()
        if (imageUrl.isNotEmpty()){
            binding.imageView4.visibility = View.VISIBLE
            Glide.with(this).load(imageUrl).into(binding.imageView4)
        }

        binding.titleTeachig.text = title

        binding.teachingAll.text = description

        binding.backReadT.setOnClickListener {
            finish()
        }

    }
}