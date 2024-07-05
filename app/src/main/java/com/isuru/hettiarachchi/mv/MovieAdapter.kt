package com.isuru.hettiarachchi.mv

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MovieAdapter(private val movies: List<Movie>,private val context: Context) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieYear: TextView = view.findViewById(R.id.movie_year)
        val movieRuntime: TextView = view.findViewById(R.id.movie_runtime)
        val movieGenres: TextView = view.findViewById(R.id.movie_genres)
        val movieSummary: TextView = view.findViewById(R.id.movie_summary)
        val movieTorrent720p: Button = view.findViewById(R.id.movie_torrent_720p)
        val movieTorrent1080p: Button = view.findViewById(R.id.movie_torrent_1080p)
        val movieTorrent2160p: Button = view.findViewById(R.id.movie_torrent_2160p)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.title
        holder.movieYear.text = movie.year.toString()

        // Convert runtime in minutes to hours and minutes
        val runtimeInMinutes = movie.runtime
        val hours = runtimeInMinutes / 60
        val minutes = runtimeInMinutes % 60
        val runtimeString = "${hours}h ${minutes}min"
        holder.movieRuntime.text = runtimeString
        holder.movieGenres.text = movie.genres?.joinToString(", ")
        holder.movieSummary.text = movie.summary

        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        var found720p =false
        var found1080p = false
        var found2160p = false

        for (torrent in movie.torrents) {
            when (torrent.quality) {
                "720p" -> {

                    if (!found720p) {
                        holder.movieTorrent720p.visibility = View.VISIBLE


                        val urlEncodeMovieName= URLEncoder.encode(movie.title, StandardCharsets.UTF_8.toString())
                        val torrentLink = "magnet:?xt=urn:btih:${torrent.hash}&dn=${urlEncodeMovieName}&tr=udp://open.demonii.com:1337/announce&tr=udp://tracker.openbittorrent.com:80&tr=dp://tracker.coppersurfer.tk:6969&tr=udp://glotorrents.pw:6969/announce&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://torrent.gresille.org:80/announce&tr=udp://p4p.arenabg.com:1337&tr=udp://tracker.leechers-paradise.org:6969&tr=udp://tracker.internetwarriors.net:1337"


                        holder.movieTorrent720p.setOnClickListener {
                            // Handle 720p torrent button click

                            val clip = ClipData.newPlainText("720p",torrentLink)
                            clipboardManager.setPrimaryClip(clip)

                            Toast.makeText(context, "720p MagnetLink Copied!", Toast.LENGTH_SHORT).show()
                        }

                        //println(torrent.hash)
                        found720p = true
                    }


                    }

                "1080p" -> {
                    if (!found1080p) {
                        holder.movieTorrent1080p.visibility = View.VISIBLE


                        val urlEncodeMovieName= URLEncoder.encode(movie.title, StandardCharsets.UTF_8.toString())
                        val torrentLink = "magnet:?xt=urn:btih:${torrent.hash}&dn=${urlEncodeMovieName}&tr=udp://open.demonii.com:1337/announce&tr=udp://tracker.openbittorrent.com:80&tr=dp://tracker.coppersurfer.tk:6969&tr=udp://glotorrents.pw:6969/announce&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://torrent.gresille.org:80/announce&tr=udp://p4p.arenabg.com:1337&tr=udp://tracker.leechers-paradise.org:6969&tr=udp://tracker.internetwarriors.net:1337"


                        holder.movieTorrent1080p.setOnClickListener {
                            // Handle 1080 torrent button click

                            val clip = ClipData.newPlainText("1080p",torrentLink)
                            clipboardManager.setPrimaryClip(clip)

                            Toast.makeText(context, "1080p MagnetLink Copied!", Toast.LENGTH_SHORT).show()
                        }

                        //println(torrent.hash)
                        found1080p = true
                    }
                }
                "2160p" -> {
                    if (!found2160p) {
                        holder.movieTorrent2160p.visibility = View.VISIBLE


                        val urlEncodeMovieName= URLEncoder.encode(movie.title, StandardCharsets.UTF_8.toString())
                        val torrentLink = "magnet:?xt=urn:btih:${torrent.hash}&dn=${urlEncodeMovieName}&tr=udp://open.demonii.com:1337/announce&tr=udp://tracker.openbittorrent.com:80&tr=dp://tracker.coppersurfer.tk:6969&tr=udp://glotorrents.pw:6969/announce&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://torrent.gresille.org:80/announce&tr=udp://p4p.arenabg.com:1337&tr=udp://tracker.leechers-paradise.org:6969&tr=udp://tracker.internetwarriors.net:1337"


                        holder.movieTorrent2160p.setOnClickListener {
                            // Handle 2160p torrent button click

                            val clip = ClipData.newPlainText("2160p",torrentLink)
                            clipboardManager.setPrimaryClip(clip)

                            Toast.makeText(context, "2160p MagnetLink Copied!", Toast.LENGTH_SHORT).show()
                        }

                        //println(torrent.hash)
                        found2160p = true
                    }
                }
            }
        }

        Glide.with(holder.itemView.context)
            .load(movie.medium_cover_image)
            .into(holder.moviePoster)
    }

    override fun getItemCount() = movies.size
}
