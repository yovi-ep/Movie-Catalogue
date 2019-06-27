package com.yeputra.moviecatalogue.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.utils.AppBarStateChangeListener
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : BaseToolbarActivity<MovieViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val movie = intent.getParcelableExtra<Movie>(Constans.INTENT_DATA)

        tv_description.text = movie.overview
        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_LARGE + movie.backdrop_path)
                .into(img_poster)

        appBarLayout.addOnOffsetChangedListener(AppBarStateChangeListener { state ->
            if (state == AppBarStateChangeListener.State.COLLAPSED) {
                toolbar_title.text = movie.title
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            } else {
                toolbar_title.text = ""
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
            }
        })


    }

    override fun setToolbar(): Toolbar = toolbar

    override fun setButtonBack(): Boolean = true

    override fun initViewModel(): MovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
}
