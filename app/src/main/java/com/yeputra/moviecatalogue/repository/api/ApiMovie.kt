package com.yeputra.moviecatalogue.repository.api

import com.yeputra.moviecatalogue.BuildConfig.API_KEY
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.ReleaseResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovie {

    @GET("discover/movie?api_key=$API_KEY")
    fun getMovies(@Query("language") lang: String) : Observable<MovieResponse>

    @GET("discover/movie?api_key=$API_KEY")
    fun getRelease(
            @Query("primary_release_date.gte") startDate: String,
            @Query("primary_release_date.lte") endDate: String,
            @Query("language") lang: String
    ) : Observable<ReleaseResponse>

    @GET("search/movie?api_key=$API_KEY")
    fun searchMovies(
            @Query("language") lang: String,
            @Query("query") q: String
    ) : Observable<SearchResponse>
}