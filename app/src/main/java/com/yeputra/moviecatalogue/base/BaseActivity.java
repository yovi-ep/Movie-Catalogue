package com.yeputra.moviecatalogue.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by yovi.putra
 *    on 09/Mar/2019 10:56
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
public abstract class BaseActivity<viewmodel extends IBaseViewModel>
        extends AppCompatActivity
        implements IBaseView {
    protected final String TAG = BaseActivity.class.getSimpleName();

    protected viewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = initViewModel();
    }

    @Override
    protected void onDestroy() {
        if(viewmodel != null)
            viewmodel.onDestroy();
        super.onDestroy();
        Log.d(TAG, "Activity destroy");
    }

    protected abstract viewmodel initViewModel();

    @Override
    public Context getContextView() {
        return this;
    }

    @Override
    public void onShowProgressbar() {
        Log.d(TAG, "show progressbar");
    }

    @Override
    public void onHideProgressbar() {
        Log.d(TAG, "hide progressbar");
    }

    @Override
    public void onViewModelFailed(@Nullable String message) {
        Log.d(TAG, "Presenter failed");
    }
}