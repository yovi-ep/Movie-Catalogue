package com.yeputra.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.MovieAdapter
import com.yeputra.moviecatalogue.base.BaseFragment
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFm : BaseFragment<MovieViewModel>() {

    private lateinit var adapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView(), R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            viewmodel.getMovie().observe(this, setMovies)
        }

        adapter = MovieAdapter { movie ->
            val `in` = Intent(contextView(), DetailMovieActivity::class.java)
            `in`.putExtra(Constans.INTENT_DATA, movie)
            startActivity(`in`)
        }

        list_item.layoutManager = GridLayoutManager(contextView(), 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter
        viewmodel.getMovie().observe(this, setMovies)
    }

    private val setMovies = Observer<MovieResponse> {
        it.results?.let { it2 -> adapter.setItem(it2) }
    }

    override fun initViewModel(): MovieViewModel {
        val vm = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        vm.setupView(this)
        return vm
    }

    override fun onShowProgressbar() {
        swiperefresh.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh.isRefreshing = false
    }
}
