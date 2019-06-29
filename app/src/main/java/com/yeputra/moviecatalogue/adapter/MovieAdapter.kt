package com.yeputra.moviecatalogue.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseRecyclerViewAdapter
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_MEDIUM
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_URL
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieAdapter(
        private val listener: (Movie) -> Unit
) : BaseRecyclerViewAdapter<MovieAdapter.VHolder, Movie>() {

    override fun onBindViewHolder(holder: VHolder, item: Movie, position: Int) {
        holder.binding(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_movie, parent, false))
    }

    class VHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun binding(movie: Movie, listener: (Movie) -> Unit) {
            var year = ""

            movie.release_date?.let {
                if (it.length > 4) year = "(${it.substring(0,4)})"
            }

            tv_title.text = "${movie.title?:"-"} $year"
            tv_rating.text = movie.vote_average.toString()

            Glide.with(containerView.context)
                    .load(POSTER_URL + POSTER_MEDIUM + movie.poster_path)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(img_poster)
            containerView.setOnClickListener { listener(movie) }
        }
    }
}
