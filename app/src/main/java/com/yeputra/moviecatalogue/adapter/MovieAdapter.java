package com.yeputra.moviecatalogue.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.base.BaseListAdapter;
import com.yeputra.moviecatalogue.base.OnClickListItemListener;
import com.yeputra.moviecatalogue.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseListAdapter<Movie> {
    private OnClickListItemListener<Movie> listener;

    public MovieAdapter(OnClickListItemListener<Movie> listener) {
        this.listener = listener;
    }
    @Override
    protected int initItemView() {
        return R.layout.item_movie;
    }

    @Override
    protected void binding(View view, Movie movie) {
        new VHolder(view, movie, listener);
    }

    class VHolder {
        private OnClickListItemListener<Movie> listener;
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDesc;

        VHolder(View view, Movie movie, OnClickListItemListener<Movie> listener) {
            ButterKnife.bind(this, view);
            this.listener = listener;

            imgPoster.setImageResource(movie.getIcon());
            tvTitle.setText(movie.getTitle());
            tvDesc.setText(movie.getDescription());

            view.setOnClickListener(v -> {
                listener.OnClickItem(movie);
            });
        }
    }
}
