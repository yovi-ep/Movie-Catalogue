package com.yeputra.moviecatalogue.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment


/**
 * Created by yovi.putra
 * on 10/Mar/2019 11:23
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */

abstract class BaseFragment<viewmodel : IBaseViewModel> : Fragment(), IBaseView {

    private val TAG = BaseFragment::class.java.simpleName

    private var activity: IBaseView? = null

    protected lateinit var viewmodel: viewmodel

    protected abstract fun initViewModel(): viewmodel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as IBaseView
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = initViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewmodel.onDestroy()
    }

    override fun contextView(): Context = context as Context

    override fun onShowProgressbar() {}

    override fun onHideProgressbar() {}

    override fun onPushInformation(message: String?, data: Any?) {}
}