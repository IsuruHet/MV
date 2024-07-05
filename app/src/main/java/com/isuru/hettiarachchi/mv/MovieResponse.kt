package com.isuru.hettiarachchi.mv

data class MovieResponse (
    val status:String,
    val data:MovieData

)

data class MovieData(
    val movie_count:Int,
    val limit:Int,
    val page_number:Int,
    val movies:List<Movie>
)

data class Movie(
    val id:Int,
    val title:String,
    val year:Int,
    val runtime:Int,
    val genres:List<String>?,
    val summary:String,
    val medium_cover_image:String,
    val torrents:List<Torrent>
)

data class Torrent(
    val hash:String,
    val quality:String
)