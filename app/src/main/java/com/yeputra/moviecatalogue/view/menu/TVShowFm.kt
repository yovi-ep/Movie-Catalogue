package com.yeputra.moviecatalogue.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.MovieAdapter
import com.yeputra.moviecatalogue.base.BaseFragment
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.model.SearchResponse
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.view.detail.DetailMovieActivity
import com.yeputra.moviecatalogue.viewmodel.TVViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class TVShowFm : BaseFragment<TVViewModel>() {
    private lateinit var adapter: MovieAdapter
    private var movieResponse: MovieResponse? = null
    private var tempQuery = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView(), R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            viewmodel?.getTVShow()?.observe(this, setTVShow)
        }

        adapter = MovieAdapter {
            val intent = Intent(contextView(), DetailMovieActivity::class.java)
            intent.putExtra(Constans.INTENT_DATA, it)
            intent.putExtra(Constans.INTENT_DATA_2, FilmType.TVSHOW)
            startActivity(intent)
        }

        list_item.layoutManager = GridLayoutManager(contextView(), 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter

        restoreSaveInstanceState(savedInstanceState)
    }

    override fun initViewModel(): TVViewModel {
        val vm = ViewModelProviders.of(this).get(TVViewModel::class.java)
        vm.setupView(this)
        return vm
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val mSearch = menu.findItem(R.id.menu_search)
        val searchView = mSearch.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { doSearch(it) }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    if (query != tempQuery)
                        doSearch(query)
                }
                return true
            }
        })
    }

    private fun doSearch(query: String?) {
        tempQuery = query.toString()
        if (query.isNullOrEmpty()) {
            viewmodel?.getTVShow()?.observe(this, setTVShow)
        } else {
            viewmodel?.searchTVShow(query)?.observe(this, setSearch)
        }
    }

    private val setTVShow = Observer<MovieResponse> {
        movieResponse = it.copy()
        it.results?.let { it1 -> adapter.setItem(it1) }
    }

    private val setSearch = Observer<SearchResponse> {
        movieResponse = MovieResponse(it.page, it.results, it.total_pages, it.total_results)
        it.results?.let { data -> adapter.setItem(data) }
    }

    override fun onShowProgressbar() {
        swiperefresh?.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh?.isRefreshing = false
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            movieResponse = it.getParcelable(Constans.INTENT_DATA)
            movieResponse?.let { it2 ->
                it2.results?.let { it1 -> adapter.setItem(it1) }
            }
        }?: run {
            viewmodel?.getTVShow()?.observe(this, setTVShow)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constans.INTENT_DATA, movieResponse)
    }
}
