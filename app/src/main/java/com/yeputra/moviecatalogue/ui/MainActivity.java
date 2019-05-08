package com.yeputra.moviecatalogue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.adapter.MovieAdapter;
import com.yeputra.moviecatalogue.base.BaseActivity;
import com.yeputra.moviecatalogue.base.OnClickListItemListener;
import com.yeputra.moviecatalogue.model.Movie;
import com.yeputra.moviecatalogue.presenter.DataPresenter;
import com.yeputra.moviecatalogue.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity<DataPresenter> implements OnClickListItemListener<Movie> {

    @BindView(R.id.list_item)
    ListView listView;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new MovieAdapter(this);
        listView.setAdapter(adapter);
        adapter.addItem(presenter.getMovies());
    }

    @Override
    public DataPresenter initPresenter() {
        return new DataPresenter(this);
    }

    @Override
    public void OnClickItem(Movie movie) {
        startActivity(new Intent(
                this,
                DetailMovieActivity.class
        ).putExtra(Constans.INTENT_DATA, movie));
    }
}
