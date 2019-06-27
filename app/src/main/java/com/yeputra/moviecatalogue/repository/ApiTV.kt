package com.yeputra.moviecatalogue.repository

import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.utils.Constans
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTV {

    @GET("tv?api_key=${Constans.API_KEY}")
    fun getTV(@Query("language") lang: String) : Observable<MovieResponse>

}