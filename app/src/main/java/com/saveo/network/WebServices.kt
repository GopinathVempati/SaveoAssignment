package com.saveo.network

import com.saveo.model.MovieDetailsResponse
import com.saveo.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @Headers("Content-Type: application/json")
    @GET("movie/popular")
    fun getPopularMoviesList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") limit: Int
    ): Call<MoviesResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): Response<MovieDetailsResponse>

}
