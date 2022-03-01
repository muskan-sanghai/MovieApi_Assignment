package com.example.movieapi.services

import com.example.movieapi.models.MovieListResponse
import com.example.movieapi.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("{movie_id}?api_key=b26cfb669431d1292d3533b6164cc5c2")
    fun getMovieDetail (@Path("movie_id") movieId:String) : Call<Movie>
    @GET("popular?api_key=b26cfb669431d1292d3533b6164cc5c2")
    fun getPopularMovieList () : Call<MovieListResponse>
}