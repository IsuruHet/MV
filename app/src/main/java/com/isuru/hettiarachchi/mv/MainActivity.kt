package com.isuru.hettiarachchi.mv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var downloadButton: Button
    private lateinit var noInternetView: LinearLayout
    private lateinit var toggleButton:ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Dark Mode

        toggleButton = findViewById(R.id.toggleButton)


        toggleButton.setOnCheckedChangeListener{ _,isChecked->
            if (isChecked){
                setTheme(R.style.AppTheme)
            }else{
                setTheme(R.style.DarkTheme)
            }

        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noInternetView = findViewById(R.id.noInternetView)

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    performSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally handle text changes as the user types
                // You can perform search as the user types here if needed
                return true
            }
        })

        val retryButton = noInternetView.findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener {
            observeMovies()
        }

        observeMovies()

        downloadButton = findViewById(R.id.downloadButton)
        downloadButton.setOnClickListener {
            val intent = Intent(this, DownloadActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeMovies() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Observe the movies LiveData from ViewModel
            movieViewModel.movies.observe(this) { movies ->
                recyclerView.adapter = MovieAdapter(movies ?: listOf(), this)
                recyclerView.visibility = View.VISIBLE
                noInternetView.visibility = View.GONE
            }
        } else {
            recyclerView.visibility = View.GONE
            noInternetView.visibility = View.VISIBLE
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun performSearch(query: String) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Observe the searchMovies LiveData from ViewModel
            movieViewModel.searchMovies(query).observe(this) { movies ->
                recyclerView.adapter = MovieAdapter(movies ?: listOf(), this)
            }
        } else {
            recyclerView.visibility = View.GONE
            noInternetView.visibility = View.VISIBLE
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }
}

