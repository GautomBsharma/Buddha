package com.mksolution.buddasspring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mksolution.buddasspring.databinding.ActivityMeditationRuleBinding

class MeditationRuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeditationRuleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationRuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView7.setOnClickListener {
            finish()
        }
    }
}