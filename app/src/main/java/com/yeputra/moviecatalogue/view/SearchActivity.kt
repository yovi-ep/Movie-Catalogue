package com.yeputra.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.MovieAdapter
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.SearchResponse
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.view.detail.DetailMovieActivity
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import com.yeputra.moviecatalogue.viewmodel.TVViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.app_bar.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SearchActivity : BaseToolbarActivity<MovieViewModel>() {

    private lateinit var tvViewModel: TVViewModel
    private lateinit var adapter: MovieAdapter
    private var searchResponse : SearchResponse? = null

    private var query = ""

    override fun setToolbar(): Toolbar = toolbar

    override fun setButtonBack(): Boolean = true

    override fun initViewModel(): MovieViewModel {
        tvViewModel = ViewModelProviders.of(this).get(TVViewModel::class.java).apply {
            setupView(this@SearchActivity)
        }

        return ViewModelProviders.of(this).get(MovieViewModel::class.java).apply {
            setupView(this@SearchActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupData()
        setupAppearance()
    }

    private fun setupData() {
        adapter = MovieAdapter { movie ->
            val `in` = Intent(contextView(), DetailMovieActivity::class.java)
            `in`.putExtra(Constans.INTENT_DATA, movie)
            `in`.putExtra(Constans.INTENT_DATA_2,
                    if (rb_group.checkedRadioButtonId == R.id.rb_film)
                        FilmType.MOVIE
                    else
                        FilmType.TVSHOW
            )
            startActivityForResult(`in`, 0)
        }
    }

    private fun setupAppearance() {
        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView(), R.color.colorAccent))
        swiperefresh.setOnRefreshListener { querying() }

        list_item.layoutManager = GridLayoutManager(contextView(), 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter

        RxTextView
                .textChanges(et_query)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.toString()
                }
                .filter {
                    it.isNotEmpty() && it != query
                }
                .subscribe {
                    query = it
                    querying()
                }
    }

    private fun querying() {
        when (rb_group.checkedRadioButtonId) {
            R.id.rb_film -> {
                viewmodel.searchMovie(query).observe(this, setMovies)
            }
            R.id.rb_tvshow -> {
                tvViewModel.searchTVShow(query).observe(this, setMovies)
            }
        }
    }

    private val setMovies = Observer<SearchResponse> {
        searchResponse = it.copy()
        it.results?.let { it1 -> adapter.setItem(it1) }
        onHideProgressbar()
    }

    override fun onShowProgressbar() {
        swiperefresh?.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh?.isRefreshing = false
    }
}
