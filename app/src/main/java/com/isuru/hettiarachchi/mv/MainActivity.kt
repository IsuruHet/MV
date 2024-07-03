package com.isuru.hettiarachchi.mv

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

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

        observeMovies()
    }

    private fun observeMovies() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Observe the movies LiveData from ViewModel
            movieViewModel.movies.observe(this) { movies ->
                recyclerView.adapter = MovieAdapter(movies ?: listOf(),this)
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun performSearch(query: String) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Observe the searchMovies LiveData from ViewModel
            movieViewModel.searchMovies(query).observe(this) { movies ->
                recyclerView.adapter = MovieAdapter(movies ?: listOf(),this)
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
