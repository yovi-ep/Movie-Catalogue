package com.yeputra.moviecatalogue.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yovi.putra
 *    on 16/Mar/2019 13:40
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
object RxUtils {
    fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}