package com.yeputra.moviecatalogue.base

import androidx.lifecycle.ViewModel
import com.yeputra.moviecatalogue.utils.RestClient
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    protected var subscriber: Disposable? = null

    inline fun <reified api> api() : api {
        return RestClient().get().create(api::class.java)
    }

    abstract fun onResponseSuccess(data: Any)

    override fun onDestroy() {
        subscriber?.dispose()
    }

    fun getLocale() : String {
        val lang = Locale.getDefault().language
        return if (lang == "in")
            "id"
        else lang
    }

    fun onSuccess(): Consumer<Any> =
            Consumer {
                onResponseSuccess(it)
            }

    fun onFailed(): Consumer<Any> =
            Consumer {}
}