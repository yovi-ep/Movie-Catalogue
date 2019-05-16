package com.yeputra.moviecatalogue.presenter;

import android.content.res.TypedArray;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.base.BasePresenter;
import com.yeputra.moviecatalogue.base.IBaseView;
import com.yeputra.moviecatalogue.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DataPresenter extends BasePresenter {

    public DataPresenter(IBaseView activity) {
        super(activity);

    }

    public void getMovies() {
        view.showProgressbar();

        TypedArray movieIcon = ctx.getResources().obtainTypedArray(R.array.movies_icon);
        String[] movieTitle = ctx.getResources().getStringArray(R.array.movies_title);
        String[] movieDescription = ctx.getResources().getStringArray(R.array.movies_description);
        String[] movieYear = ctx.getResources().getStringArray(R.array.movies_year);

        List<Movie> movies = new ArrayList<>();
        for (int i= 0; i < movieTitle.length; i++) {
            movies.add(new Movie(
                    movieIcon.getResourceId(i, 0),
                    movieTitle[i],
                    movieDescription[i],
                    movieYear[i]
            ));
        }
        view.onPresenterSuccess(movies);
        movieIcon.recycle();
        view.hideProgressbar();
    }

    public void getTVMovies() {
        view.showProgressbar();

        TypedArray movieIcon = ctx.getResources().obtainTypedArray(R.array.movies_icon);
        String[] movieTitle = ctx.getResources().getStringArray(R.array.movies_title);
        String[] movieDescription = ctx.getResources().getStringArray(R.array.movies_description);
        String[] movieYear = ctx.getResources().getStringArray(R.array.movies_year);

        List<Movie> movies = new ArrayList<>();
        for (int i= 0; i < movieTitle.length; i++) {
            movies.add(new Movie(
                    movieIcon.getResourceId(i, 0),
                    movieTitle[i],
                    movieDescription[i],
                    movieYear[i]
            ));
        }
        view.onPresenterSuccess(movies);
        movieIcon.recycle();
        view.hideProgressbar();
    }

    @Override
    public void onDestroyPresenter() {

    }
}
