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

public abstract class BaseFragment<viewmodel extends IBaseViewModel>
    extends Fragment
    implements IBaseView {

    private final String TAG = BaseFragment.class.getSimpleName();

    private IBaseView activity;
    protected viewmodel viewmodel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (IBaseView) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewmodel = initPresenter();
    }

    protected abstract viewmodel initPresenter();

    @Override
    public Context getContextView() {
        return getContext();
    }

    @Override
    public void onViewModelFailed(String message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewmodel.onDestroy();
    }
}