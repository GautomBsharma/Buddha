package com.mksolution.buddasspring.Fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import com.mksolution.buddasspring.MaditatiomtwoActivity
import com.mksolution.buddasspring.MeditationThreeActivity
import com.mksolution.buddasspring.R
import com.mksolution.buddasspring.RelaxMeditationActivity
import com.mksolution.buddasspring.databinding.FragmentMeditationBinding


class MeditationFragment : Fragment() {
    private lateinit var binding: FragmentMeditationBinding
     // To track time
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMeditationBinding.inflate(layoutInflater)

         binding.button.setOnClickListener {
             startActivity(Intent(requireContext(),RelaxMeditationActivity::class.java))
         }
         binding.button2.setOnClickListener {
             startActivity(Intent(requireContext(),MaditatiomtwoActivity::class.java))
         }
         binding.button3.setOnClickListener {
             startActivity(Intent(requireContext(),MeditationThreeActivity::class.java))
         }

        return binding.root
    }


}