package com.yeputra.moviecatalogue.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding.widget.RxSearchView
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.MovieAdapter
import com.yeputra.moviecatalogue.base.BaseFragment
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.utils.Constans.Companion.CHANGE_LOCAL
import com.yeputra.moviecatalogue.utils.Constans.Companion.INTENT_DATA
import com.yeputra.moviecatalogue.utils.Constans.Companion.INTENT_DATA_2
import com.yeputra.moviecatalogue.view.detail.DetailMovieActivity
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MovieFm : BaseFragment<MovieViewModel>() {

    private lateinit var adapter: MovieAdapter
    private var movieResponse: MovieResponse? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView(), R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            viewmodel?.getMovie()?.observe(this, setMovies)
        }

        adapter = MovieAdapter { movie ->
            val `in` = Intent(contextView(), DetailMovieActivity::class.java)
            `in`.putExtra(INTENT_DATA, movie)
            `in`.putExtra(INTENT_DATA_2, FilmType.MOVIE)
            startActivity(`in`)
        }

        list_item.layoutManager = GridLayoutManager(contextView(), 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter

        restoreSaveInstanceState(savedInstanceState)
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            movieResponse = it.getParcelable(INTENT_DATA)
            movieResponse?.let { it2 ->
                it2.results?.let { it1 -> adapter.setItem(it1) }
            }
        }?: run {
            viewmodel?.getMovie()?.observe(this, setMovies)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(INTENT_DATA, movieResponse)
    }

    private val setMovies = Observer<MovieResponse> {
        movieResponse = it.copy()
        it.results?.let { it2 -> adapter.setItem(it2) }
    }

    override fun initViewModel(): MovieViewModel {
        val vm = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        vm.setupView(this)
        return vm
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearch = menu.findItem(R.id.menu_search)
        val searchView = mSearch.actionView as android.widget.SearchView

        RxSearchView
                .queryTextChanges(searchView)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.toString()
                }
                .subscribe {
                    adapter.filter.filter(it)
                }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onShowProgressbar() {
        swiperefresh?.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh?.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == CHANGE_LOCAL) {
            viewmodel?.getMovie()?.observe(this, setMovies)
        }
    }
}
