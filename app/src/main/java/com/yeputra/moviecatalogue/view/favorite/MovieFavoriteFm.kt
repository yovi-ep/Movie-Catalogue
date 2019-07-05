package com.yeputra.moviecatalogue.view.favorite

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
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.view.detail.DetailMovieActivity
import com.yeputra.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFavoriteFm : BaseFragment<FavoriteViewModel>() {

    private lateinit var adapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(contextView(), R.color.colorAccent))
        swiperefresh.setOnRefreshListener { loadData() }

        adapter = MovieAdapter { movie ->
            val `in` = Intent(contextView(), DetailMovieActivity::class.java)
            `in`.putExtra(Constans.INTENT_DATA, movie)
            `in`.putExtra(Constans.INTENT_DATA_2, FilmType.MOVIE)
            startActivityForResult(`in`, 0)
        }

        list_item.layoutManager = GridLayoutManager(contextView(), 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter
        loadData()
    }

    private fun loadData() {
        viewmodel.getMovieFavorite().observe(this, setMovies)
    }

    private val setMovies = Observer<MutableList<Movie>> {
        adapter.setItem(it)
        onHideProgressbar()
    }

    override fun initViewModel(): FavoriteViewModel {
        val vm = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        vm.setupView(this)
        return vm
    }

    override fun onShowProgressbar() {
        swiperefresh?.isRefreshing = true
    }

    override fun onHideProgressbar() {
        swiperefresh?.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadData()
    }
}
