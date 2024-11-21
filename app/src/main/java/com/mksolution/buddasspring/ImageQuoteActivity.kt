package com.mksolution.buddasspring

import android.annotation.SuppressLint
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
import com.mksolution.buddasspring.Adaptars.ImageQuoteAdapter
import com.mksolution.buddasspring.Models.ImageQuote
import com.mksolution.buddasspring.Models.Stories
import com.mksolution.buddasspring.databinding.ActivityImageQuoteBinding

class ImageQuoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageQuoteBinding
    private lateinit var adapter: ImageQuoteAdapter
    private lateinit var list: ArrayList<ImageQuote>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycleImageQuote.layoutManager = LinearLayoutManager(this)
        list = arrayListOf()
        adapter = ImageQuoteAdapter(this,list)
        binding.recycleImageQuote.adapter = adapter

        if (isNetworkAvailable(this)) {
            // Internet is available, retrieve data
            getData()
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


    private fun getData() {
        binding.progressBar.visibility = View.VISIBLE
        val reff = FirebaseDatabase.getInstance().reference.child("ImageQuote")

        reff.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    list.clear()  // Clear the list before adding new items
                    for (snap in snapshot.children) {
                        val datt = snap.getValue(ImageQuote::class.java)
                        datt?.let { list.add(it) }
                    }
                    list.reverse()
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