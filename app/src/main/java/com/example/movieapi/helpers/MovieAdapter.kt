package com.example.movieapi.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.movieapi.R
import com.example.movieapi.models.MovieListResponse

class MovieAdapter(private val moviesList: List<MovieListResponse.Movie>, private val onClick: (MovieListResponse.Movie)-> Unit) :RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return ViewHolder(view, onClick)
    }


    override fun getItemCount(): Int {

        return moviesList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${moviesList.size} ")

        return holder.bind(moviesList[position])

    }
    class ViewHolder(itemView : View, private val onClick: (MovieListResponse.Movie)-> Unit) :RecyclerView.ViewHolder(itemView) {


        var imageView = itemView.findViewById<ImageView>(R.id.movie_poster)
        var tvTitle = itemView.findViewById<TextView>(R.id.movie_title)
        var tvCases = itemView.findViewById<TextView>(R.id.release_date)
        fun bind(movie: MovieListResponse.Movie) {
            itemView.setOnClickListener{

                onClick(movie)


            }
            val name ="Release Date: ${movie.release_date.toString()}"
            tvTitle.text = movie.title
            tvCases.text = name
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.poster_path).into(imageView)
        }

    }
}