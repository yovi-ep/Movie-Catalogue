package com.yeputra.moviecatalogue.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by yovi.putra
 * on 09/Mar/2019 10:56
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
abstract class BaseActivity<viewmodel : IBaseViewModel> : AppCompatActivity(), IBaseView {
    override fun contextView(): Context = this

    private val TAG = BaseActivity::class.java.simpleName

    protected lateinit var viewmodel: viewmodel

    protected abstract fun initViewModel(): viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = initViewModel()
    }

    override fun onDestroy() {
        viewmodel?.onDestroy()
        super.onDestroy()
    }

    override fun onPushInformation(message: String?, data: Any?) {}

    override fun onShowProgressbar() {}

    override fun onHideProgressbar() {}

}