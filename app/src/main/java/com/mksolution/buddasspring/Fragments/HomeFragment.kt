package com.mksolution.buddasspring.Fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mksolution.buddasspring.*
import com.mksolution.buddasspring.Adaptars.SliderAdapter
import com.mksolution.buddasspring.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)



        val imageList = listOf(
            R.drawable.slid1,
            R.drawable.slid2,
            R.drawable.slid3,
            R.drawable.slide4,
            R.drawable.slid5,
            R.drawable.slid6
        )

        // Set the adapter for ViewPager2
        val adapter = SliderAdapter(imageList)
        binding.viewPager.adapter = adapter
        autoScrollViewPager(binding.viewPager)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.goBreathEqual.setOnClickListener {
            startActivity(Intent(requireContext(),EqualBreathActivity::class.java))
        }
        binding.goBreathAdvance.setOnClickListener {
            startActivity(Intent(requireContext(),AdvanceBreathingActivity::class.java))
        }
        binding.goCounter.setOnClickListener {
            startActivity(Intent(requireContext(),CounterActivity::class.java))
        }
        binding.goStories.setOnClickListener {
            startActivity(Intent(requireContext(),StoriesActivity::class.java))
        }
        binding.goTeaching.setOnClickListener {
            startActivity(Intent(requireContext(),TeachingActivity::class.java))
        }
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {


                R.id.addThing -> {
                    showConfirmationDialog()
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }
        binding.appManu.setOnClickListener {
            binding.drawerLayout.open()
        }





        return binding.root
    }

    private fun autoScrollViewPager(viewPager: ViewPager2) {
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = viewPager.currentItem
                val nextItem = if (currentItem == viewPager.adapter!!.itemCount - 1) 0 else currentItem + 1
                viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 2000) // Auto-scroll every 3 seconds
            }
        }
        handler.post(runnable)
    }
    @SuppressLint("MissingInflatedId")
    fun showConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)
        val etConfirmCode: EditText = dialogView.findViewById(R.id.etConfirmCodee)
        val btnConfirm: Button = dialogView.findViewById(R.id.btnConfirme)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Confirmation Code")
            .setCancelable(true)
            .setNegativeButton("Cancel", null)

        val dialog = dialogBuilder.create()

        btnConfirm.setOnClickListener {
            val confirmCode = etConfirmCode.text.toString()
            if (confirmCode == "12355") {
                startActivity(Intent(requireContext(),AddThingActivity::class.java))
                dialog.dismiss()
            } else {
                // Show an error message or handle incorrect confirmation code
                etConfirmCode.error = "Incorrect confirmation code"
            }
        }

        dialog.show()
    }

}