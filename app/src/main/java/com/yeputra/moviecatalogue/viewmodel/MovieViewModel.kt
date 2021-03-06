package com.yeputra.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import com.yeputra.moviecatalogue.repository.api.ApiMovie
import com.yeputra.moviecatalogue.utils.RxUtils

class MovieViewModel : BaseViewModel() {
    private val movieLiveData = MutableLiveData<MovieResponse>()
    private val searchMovieLiveData = MutableLiveData<SearchResponse>()

    fun getMovie() : LiveData<MovieResponse> {
        view?.onShowProgressbar()
        subscriber = api<ApiMovie>()
                .getMovies(getLocale())
                .compose(RxUtils.applyObservableAsync())
                .subscribe(onSuccess(), onFailed())

        return movieLiveData
    }

    fun searchMovie(query: String) : LiveData<SearchResponse> {
        view?.onShowProgressbar()
        subscriber = api<ApiMovie>()
                .searchMovies(getLocale(), query)
                .compose(RxUtils.applyObservableAsync())
                .subscribe(onSuccess(), onFailed())

        return searchMovieLiveData
    }

    override fun onResponseSuccess(data: Any) {
        when (data) {
            is MovieResponse -> {
                movieLiveData.postValue(data)
            }
            is SearchResponse -> {
                searchMovieLiveData.postValue(data)
            }
        }
    }
}