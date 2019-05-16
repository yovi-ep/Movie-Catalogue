package com.yeputra.moviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.base.BaseRecyclerViewAdapter;
import com.yeputra.moviecatalogue.base.OnClickListItemListener;
import com.yeputra.moviecatalogue.model.Movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseRecyclerViewAdapter<MovieAdapter.VHolder, Movie> {
    private OnClickListItemListener<Movie> listener;


    public MovieAdapter(OnClickListItemListener<Movie> listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(VHolder holder, Movie item, int position) {
        holder.binding(item, this.listener);
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_movie, parent, false));
    }

    class VHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDesc;

        public VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void binding(Movie movie, OnClickListItemListener<Movie> listener) {
            imgPoster.setImageResource(movie.getIcon());
            tvTitle.setText(movie.getTitle());
            tvDesc.setText(movie.getDescription());

            itemView.setOnClickListener(v -> listener.OnClickItem(movie));
        }
    }
}
