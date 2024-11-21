package com.mksolution.buddasspring.Fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksolution.buddasspring.Adaptars.QuoteAdapter
import com.mksolution.buddasspring.ImageQuoteActivity
import com.mksolution.buddasspring.Models.Quote
import com.mksolution.buddasspring.Models.QuoteDefault
import com.mksolution.buddasspring.R
import com.mksolution.buddasspring.TodayQuoteActivity
import com.mksolution.buddasspring.databinding.FragmentQuoteBinding


class QuoteFragment : Fragment() {
    private lateinit var binding: FragmentQuoteBinding
    private var adapter: QuoteAdapter? = null
    private var quoteListDefault: ArrayList<QuoteDefault> = arrayListOf()
    private var quoteListFirebase: ArrayList<Quote> = arrayListOf()
    private var isOnline = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuoteBinding.inflate(layoutInflater)

        // Populate the list of default quotes from string resources
        val items = listOf(
            QuoteDefault(R.string.quote1),
            QuoteDefault(R.string.quote2),
            QuoteDefault(R.string.quote3),
            QuoteDefault(R.string.quote4),
            QuoteDefault(R.string.quote5),
            QuoteDefault(R.string.quote6)
        )
        quoteListDefault.addAll(items)

        // Set up RecyclerView with default quotes
        binding.recycleQuote.layoutManager = LinearLayoutManager(requireContext())

        adapter = QuoteAdapter(requireContext(), quoteListDefault)
        binding.recycleQuote.adapter = adapter


        // Handle button clicks
        binding.goImageQuote.setOnClickListener {
            startActivity(Intent(requireContext(), ImageQuoteActivity::class.java))
        }
        binding.goTodayQuote.setOnClickListener {
            startActivity(Intent(requireContext(), TodayQuoteActivity::class.java))
        }

        // Check if the network is available
        if (isNetworkAvailable(requireContext())) {
            isOnline = true
            getQuoteFromFirebase()  // Load Firebase quotes
        } else {
            isOnline = false
            showNoInternetDialog()  // Show dialog if offline
            loadDefaultQuotes()      // Load default quotes
        }

        return binding.root
    }

    // Function to check network availability
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    // Function to show default quotes when offline
    private fun loadDefaultQuotes() {
        adapter?.updateQuotes(quoteListDefault)  // Update adapter's data to default quotes
    }

    // Function to show Firebase quotes when online
    private fun getQuoteFromFirebase() {
        val reff = FirebaseDatabase.getInstance().reference.child("Quote")

        reff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    quoteListFirebase.clear()  // Clear the list before adding new items
                    for (snap in snapshot.children) {
                        val datt = snap.getValue(Quote::class.java)
                        datt?.let { quoteListFirebase.add(it) }
                    }

                    quoteListFirebase.reverse()
                    // Update the adapter to show Firebase quotes
                    adapter?.updateQuotes(quoteListFirebase)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    // Function to show no internet dialog
    private fun showNoInternetDialog() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("No Internet Connection")
            .setIcon(R.drawable.round_signal_wifi_connected_no_internet_4_24)
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}