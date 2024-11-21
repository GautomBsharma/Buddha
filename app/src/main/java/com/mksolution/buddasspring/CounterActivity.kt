package com.mksolution.buddasspring

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.mksolution.buddasspring.databinding.ActivityCounterBinding

class CounterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCounterBinding
    private var count:Int=0
    private var fullcount:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.countBtn.setOnClickListener {
            countfun()
            if (binding.switchBtn.isChecked){
                vibrate()
            }

        }
        binding.clearBtn.setOnClickListener {
            cleardata()
        }

        binding.countBtnMinas.setOnClickListener {
            if (count>=1){
                count--
                binding.tvShowCount.text = count.toString()

            }

        }

    }

    private fun vibrate() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT>=26){
            vibrator.vibrate(VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE))
        }
        else{
            vibrator.vibrate(100L)
        }

    }


    private fun cleardata() {
        count= 0
        binding.tvShowCount.text = count.toString()
        fullcount= 0
        binding.tvTotal.text = "Total : $fullcount"

    }

    private fun countfun() {
        count++
        binding.tvShowCount.text = count.toString()
        if (count==108){
            count = 0
            fullmal()
        }
    }
    private fun fullmal() {
        fullcount++
        binding.tvTotal.text = "Total : $fullcount"

    }

}