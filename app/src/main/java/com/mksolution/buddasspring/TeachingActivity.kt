package com.mksolution.buddasspring

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksolution.buddasspring.Adaptars.TeachingAdapter
import com.mksolution.buddasspring.Models.Stories
import com.mksolution.buddasspring.Models.Teaching
import com.mksolution.buddasspring.databinding.ActivityTeachingBinding

class TeachingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeachingBinding
    private lateinit var adapter: TeachingAdapter
    private lateinit var list: ArrayList<Teaching>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeachingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backTeaching.setOnClickListener {
            finish()
        }
        binding.teachingRecycle.layoutManager = LinearLayoutManager(this)
        list = arrayListOf()
        adapter = TeachingAdapter(this,list)
        binding.teachingRecycle.adapter = adapter

        if (isNetworkAvailable(this)) {
            // Internet is available, retrieve data
            getTeaching()
        } else {
            // No internet connection, show dialog

            showNoInternetDialog()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private fun showNoInternetDialog() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setIcon(R.drawable.round_signal_wifi_connected_no_internet_4_24)
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("OK") { dialog, _ ->
                //dialog.dismiss()
                binding.constraintLayout7.visibility = View.VISIBLE
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun getTeaching() {
        binding.progressBar.visibility = View.VISIBLE
        val reff = FirebaseDatabase.getInstance().reference.child("Teaching")

        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    list.clear()  // Clear the list before adding new items
                    for (snap in snapshot.children) {
                        val datt = snap.getValue(Teaching::class.java)
                        datt?.let { list.add(it) }
                    }
                    adapter.notifyDataSetChanged()  // Notify the adapter that data has changed
                }

                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}