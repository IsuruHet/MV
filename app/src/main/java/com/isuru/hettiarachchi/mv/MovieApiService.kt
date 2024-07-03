package com.isuru.hettiarachchi.mv

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("list_movies.json")
    fun getMovies(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("list_movies.json")
    fun searchMovies(
        @Query("limit")limit:Int = 20,
        @Query("query_term")query: String?=null
    ):Call<MovieResponse>

}