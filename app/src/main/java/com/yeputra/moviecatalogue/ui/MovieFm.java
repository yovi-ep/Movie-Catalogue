package com.yeputra.moviecatalogue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.adapter.MovieAdapter;
import com.yeputra.moviecatalogue.base.BaseFragment;
import com.yeputra.moviecatalogue.model.Movie;
import com.yeputra.moviecatalogue.presenter.DataPresenter;
import com.yeputra.moviecatalogue.utils.Constans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFm extends BaseFragment<DataPresenter> {

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.list_item)
    RecyclerView rvItem;

    private MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContextView(),R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getMovies());

        adapter = new MovieAdapter(movie -> {
            Intent in = new Intent(getContextView(), DetailMovieActivity.class);
            in.putExtra(Constans.INTENT_DATA, movie);
            startActivity(in);
        });

        rvItem.setLayoutManager(new LinearLayoutManager(getContextView()));
        rvItem.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvItem.setAdapter(adapter);
        presenter.getMovies();
    }

    @Override
    public void onPresenterSuccess(Object data) {
        super.onPresenterSuccess(data);
        adapter.replaceItem((List<Movie>) data);
    }

    @Override
    protected DataPresenter initPresenter() {
        return new DataPresenter(this);
    }

    @Override
    public void showProgressbar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressbar() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
