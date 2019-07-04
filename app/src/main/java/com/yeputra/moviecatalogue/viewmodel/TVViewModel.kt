package com.yeputra.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.repository.api.ApiTV
import com.yeputra.moviecatalogue.utils.RxUtils

class TVViewModel : BaseViewModel() {
    private val movieLiveData = MutableLiveData<MovieResponse>()

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
        }
    }
}