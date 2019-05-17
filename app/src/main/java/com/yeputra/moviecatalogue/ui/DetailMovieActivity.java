package com.yeputra.moviecatalogue.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.base.BaseToolbarActivity;
import com.yeputra.moviecatalogue.model.Movie;
import com.yeputra.moviecatalogue.presenter.DataPresenter;
import com.yeputra.moviecatalogue.utils.AppBarStateChangeListener;
import com.yeputra.moviecatalogue.utils.Constans;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends BaseToolbarActivity<DataPresenter> {
    @BindView(R.id.img_poster)
    ImageView imgPoster;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        Movie movie = getIntent().getParcelableExtra(Constans.INTENT_DATA);
        imgPoster.setImageResource(movie.getIcon());
        tvDescription.setText("(" + movie.getDate() + ") " + movie.getDescription());

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener(state -> {
            if (state == AppBarStateChangeListener.State.COLLAPSED) {
                toolbarTitle.setText(movie.getTitle());
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            } else {
                toolbarTitle.setText("");
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            }
        }));
    }

    @Override
    public boolean setButtonBack() {
        return true;
    }

    @Override
    protected Toolbar setToolbar() {
        return findViewById(R.id.toolbar);
    }

    @Override
    protected DataPresenter initPresenter() {
        return new DataPresenter(this);
    }
}
