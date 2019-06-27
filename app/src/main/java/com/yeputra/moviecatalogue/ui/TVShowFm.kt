package com.yeputra.moviecatalogue.ui

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
import com.yeputra.moviecatalogue.viewmodel.TVViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class TVShowFm : BaseFragment<TVViewModel>() {
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onShowProgressbar()

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView, R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            onShowProgressbar()
            viewmodel.getTVShow().observe(this, setTVShow)
        }

        adapter = MovieAdapter {
            val intent = Intent(contextView, DetailMovieActivity::class.java)
            intent.putExtra(Constans.INTENT_DATA, it)
            startActivity(intent)
        }

        list_item.layoutManager = GridLayoutManager(contextView, 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter
        viewmodel.getTVShow().observe(this, setTVShow)
    }

    private val setTVShow = Observer<MovieResponse> {
        it.results?.let { it1 -> adapter.setItem(it1) }
        onHideProgressbar()
    }

    override fun initPresenter(): TVViewModel {
        return ViewModelProviders.of(this).get(TVViewModel::class.java)
    }

    override fun onShowProgressbar() {
        swiperefresh.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh.isRefreshing = false
    }

}