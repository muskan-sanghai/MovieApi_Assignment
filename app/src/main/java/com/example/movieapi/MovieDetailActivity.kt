package com.example.movieapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.movieapi.models.Movie
import com.example.movieapi.services.MovieService
import com.example.movieapi.services.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {
    private val TAG = "MovieDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        Log.d(TAG, "onCreate: ${intent.getIntExtra("id",0)}")
        loadMovieDetail(intent.getIntExtra("id",0).toString())
    }
    private fun loadMovieDetail(id:String) {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(MovieService::class.java)
        val requestCall = destinationService.getMovieDetail(id)
        //make network call asynchronously
        requestCall.enqueue(object : Callback<Movie> {
            override fun onResponse(
                call: Call<Movie>,
                response: Response<Movie>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val movieList = response.body()!!
                    Log.d("Response", "countrylist size : ${movieList}")

                    updateUI(movieList)

                } else {
                    Toast.makeText(
                        this@MovieDetailActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(this@MovieDetailActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun updateUI(movie: Movie) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.poster_path).into(findViewById<ImageView>(R.id.movie_poster))
        findViewById<TextView>(R.id.movie_title).text = movie.title
        findViewById<TextView>(R.id.description).text = movie.overview

    }
}