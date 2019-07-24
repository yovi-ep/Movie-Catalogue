package com.yeputra.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import com.yeputra.moviecatalogue.repository.api.ApiTV
import com.yeputra.moviecatalogue.utils.RxUtils

class TVViewModel(app: Application) : BaseViewModel(app) {
    private val movieLiveData = MutableLiveData<MovieResponse>()
    private val searchTVLiveData = MutableLiveData<SearchResponse>()

    fun getTVShow() : LiveData<MovieResponse> {
        view?.onShowProgressbar()
        subscriber = api<ApiTV>()
                .getTV(getLocale())
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
                searchTVLiveData.postValue(data)
            }
        }
    }
}