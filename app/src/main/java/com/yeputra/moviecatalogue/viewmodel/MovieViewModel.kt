package com.yeputra.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import com.yeputra.moviecatalogue.repository.api.ApiMovie
import com.yeputra.moviecatalogue.utils.RxUtils

class MovieViewModel(app: Application) : BaseViewModel(app) {
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