package com.yeputra.moviecatalogue.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.app_bar.*

class DetailMovieActivity : BaseToolbarActivity<MovieViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        initViewConfigure()
    }

    @SuppressLint("SetTextI18n")
    private fun initViewConfigure() {
        var year = ""
        var adult = ""
        val movie = intent.getParcelableExtra<Movie>(Constans.INTENT_DATA)

        movie.release_date?.let {
            if (it.length > 4) year = "(${it.substring(0,4)})"
        }
        movie.adult?.let {
            if (it) adult = " | 17+"
        }

        toolbar_title.text = movie.original_title
        tv_title.text = "${movie.title?:"-"} $year"
        tv_rating.text = movie.vote_average.toString() + adult
        tv_overview.text = movie.overview

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_LARGE + movie.backdrop_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_banner)

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_MEDIUM + movie.poster_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_poster)
    }

    override fun setToolbar(): Toolbar = toolbar

    override fun setButtonBack(): Boolean = true

    override fun initViewModel(): MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
}
