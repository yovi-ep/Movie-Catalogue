package com.yeputra.moviecatalogue.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.base.BaseToolbarActivity;
import com.yeputra.moviecatalogue.base.IBasePresenter;
import com.yeputra.moviecatalogue.model.Movie;
import com.yeputra.moviecatalogue.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends BaseToolbarActivity {
    @BindView(R.id.img_poster)
    ImageView imgPoster;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        setToolbar(R.id.toolbar, R.id.toolbar_title);

        Movie movie = getIntent().getParcelableExtra(Constans.INTENT_DATA);
        setToolbarTitle(movie.getTitle());
        imgPoster.setImageResource(movie.getIcon());
        tvDescription.setText("(" + movie.getDate() + ") " + movie.getDescription());
    }

    @Override
    public boolean setButtonBack() {
        return true;
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }
}
