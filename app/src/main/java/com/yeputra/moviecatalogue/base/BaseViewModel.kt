package com.yeputra.moviecatalogue.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.ViewModel
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.model.ErrorResponse
import com.yeputra.moviecatalogue.utils.RestClient
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.json.JSONObject
import retrofit2.HttpException
import java.util.*

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    private val TAG = BaseViewModel::class.java.simpleName

    protected var subscriber: Disposable? = null

    protected var view: IBaseView? = null

    inline fun <reified api> api() : api = RestClient().get().create(api::class.java)

    abstract fun onResponseSuccess(data: Any)

    fun setupView(view: IBaseView) {
        this.view = view
    }

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
                view?.onHideProgressbar()
                when (it) {
                    is ErrorResponse -> {
                        view?.onPushInformation(it.status_code + " : " + it.status_message, null)
                    }
                    else -> onResponseSuccess(it)
                }
            }

    fun onFailed(): Consumer<Any> =
            Consumer {
                view?.onHideProgressbar()

                when (it) {
                    is HttpException -> {
                        try {
                            val errBody = it.response().errorBody()?.string()
                            val json = JSONObject(errBody)
                            view?.onPushInformation(json["status"].toString()+": " + json["error"].toString(), null)
                        } catch (e: Exception) {
                            Log.e(TAG, e.message)
                            view?.onPushInformation(view?.contextView()?.getString(R.string.server_under_maintenance), null)
                        }
                    }
                    else -> {
                        val connectivityManager = view?.contextView()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

                        if (isConnected) {
                            view?.onPushInformation(view?.contextView()?.getString(R.string.cannot_connect_toserver), null)
                        } else {
                            view?.onPushInformation(view?.contextView()?.getString(R.string.no_internet_connection), null)
                        }
                    }
                }
            }
}