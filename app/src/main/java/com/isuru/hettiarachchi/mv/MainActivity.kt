package com.isuru.hettiarachchi.mv

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (NetworkUtils.isNetworkAvailable(this)) {
            movieViewModel.movies.observe(this) { movies ->
                recyclerView.adapter = MovieAdapter(movies ?: listOf())
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
