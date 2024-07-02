package com.isuru.hettiarachchi.mv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class MovieViewModel : ViewModel() {

    val movies = liveData(Dispatchers.IO) {
        val response = RetrofitInstance.api.getMovies(20, 1).execute()
        emit(response.body()?.data?.movies)
    }

}