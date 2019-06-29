package com.yeputra.moviecatalogue.base

import android.content.Context


/**
 * Created by yovi.putra
 * on 09/Mar/2019 10:55
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
interface IBaseView {

    fun contextView(): Context

    fun onShowProgressbar()

    fun onHideProgressbar()

    fun onPushInformation(message: String?, data: Any?)

}
