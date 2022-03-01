package com.example.movieapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.helpers.MovieAdapter
import com.example.movieapi.models.MovieListResponse
import com.example.movieapi.services.MovieService
import com.example.movieapi.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMovieList()
    }


    private fun loadMovieList() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(MovieService::class.java)
        val requestCall = destinationService.getPopularMovieList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val movieList = response.body()!!
                    Log.d("Response", "countrylist size : ${movieList.results.size}")
                    var movieRecycler = findViewById(R.id.movie_recycler) as RecyclerView
                    movieRecycler.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MovieAdapter(movieList.results, onClick = {
                            Log.d("onClick", "onResponse: $it")
                            val intent = Intent(this@MainActivity,MovieDetailActivity::class.java)
                            intent.putExtra("id", it.id)
                            startActivity(intent)
                        })
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}