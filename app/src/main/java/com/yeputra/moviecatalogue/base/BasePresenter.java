package com.yeputra.moviecatalogue.base;

import android.content.Context;

/**
 * Created by yovi.putra
 *    on 10/Mar/2019 10:31
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
public abstract class BasePresenter implements IBasePresenter {
    protected IBaseView view;
    protected Context ctx;
    public BasePresenter(IBaseView activity) {
        this.view = activity;
        this.ctx = (Context) activity;
    }
}