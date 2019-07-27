package com.yeputra.moviecatalogue.repository.api

import com.yeputra.moviecatalogue.BuildConfig.API_KEY
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTV {

    @GET("discover/tv?api_key=$API_KEY")
    fun getTV(@Query("language") lang: String) : Observable<MovieResponse>

    @GET("search/tv?api_key=$API_KEY")
    fun searchTV(
            @Query("language") lang: String,
            @Query("query") q: String
    ) : Observable<SearchResponse>
}