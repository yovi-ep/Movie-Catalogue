package com.yeputra.moviecatalogue.repository.api

import com.yeputra.moviecatalogue.BuildConfig.API_KEY
import com.yeputra.moviecatalogue.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovie {

    @GET("movie?api_key=$API_KEY")
    fun getMovies(@Query("language") lang: String) : Observable<MovieResponse>

}