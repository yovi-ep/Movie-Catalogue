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
public abstract class BaseActivity<presenter extends IBasePresenter>
        extends AppCompatActivity
        implements IBaseView {
    protected final String TAG = BaseActivity.class.getSimpleName();

    protected presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        if(presenter != null)
            presenter.onDestroyPresenter();
        super.onDestroy();
        Log.d(TAG, "Activity destroy");
    }

    protected abstract presenter initPresenter();

    @Override
    public Context getContextView() {
        return this;
    }

    @Override
    public void showProgressbar() {
        Log.d(TAG, "show progressbar");
    }

    @Override
    public void hideProgressbar() {
        Log.d(TAG, "hide progressbar");
    }

    @Override
    public void onPresenterSuccess(@Nullable Object data) {
        Log.d(TAG, "Presenter success");
    }

    @Override
    public void onPresenterFailed(@Nullable String message) {
        Log.d(TAG, "Presenter failed");
    }
}