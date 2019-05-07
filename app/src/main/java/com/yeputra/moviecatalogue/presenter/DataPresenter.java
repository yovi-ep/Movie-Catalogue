package com.yeputra.moviecatalogue.presenter;

import com.yeputra.moviecatalogue.base.BasePresenter;
import com.yeputra.moviecatalogue.base.IBaseView;

public class DataPresenter extends BasePresenter {

    public DataPresenter(IBaseView activity) {
        super(activity);

    }

    public void getMovies() {
        view.showProgressbar();
        view.onPresenterSuccess(null);
        view.hideProgressbar();
    }

    @Override
    public void onDestroyPresenter() {

    }
}
