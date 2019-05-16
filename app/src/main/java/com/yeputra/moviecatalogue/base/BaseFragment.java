package com.yeputra.moviecatalogue.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by yovi.putra
 *    on 10/Mar/2019 11:23
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */

public abstract class BaseFragment<presenter extends IBasePresenter>
    extends Fragment
    implements IBaseView {

    private final String TAG = BaseFragment.class.getSimpleName();

    private IBaseView activity;
    protected presenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            activity = (IBaseView) context;
        } else {
            activity = this;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
    }

    protected abstract presenter initPresenter();

    @Override
    public Context getContextView() {
        return getContext();
    }

    @Override
    public void onPresenterSuccess(Object data) {

    }

    @Override
    public void onPresenterFailed(String message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroyPresenter();
    }
}