package com.yeputra.moviecatalogue.repository

import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.utils.Constans.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovie {

    @GET("movie?api_key=$API_KEY")
    fun getMovies(@Query("language") lang: String) : Observable<MovieResponse>

}