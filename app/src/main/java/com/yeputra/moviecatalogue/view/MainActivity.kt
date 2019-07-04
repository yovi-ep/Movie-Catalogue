package com.yeputra.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.base.ITabView
import com.yeputra.moviecatalogue.utils.*
import com.yeputra.moviecatalogue.view.menu.FavoriteFm
import com.yeputra.moviecatalogue.view.menu.MovieFm
import com.yeputra.moviecatalogue.view.menu.TVShowFm
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_tab.*


class MainActivity : BaseToolbarActivity<MovieViewModel>(), ITabView {
    private var fragmentActived: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movie = MovieFm()
        val tvShow = TVShowFm()
        val favorite = FavoriteFm()

        fragmentAdd(R.id.main_container, movie)
        fragmentAdd(R.id.main_container, tvShow)
        fragmentAdd(R.id.main_container, favorite)

        button_navigation.setOnNavigationItemSelectedListener {
            fragmentActived?.let { it1 -> fragmentHide(it1) }

            when(it.itemId) {
                R.id.menu_movie -> {
                    tablayout.gone()
                    fragmentActived = movie
                }
                R.id.menu_tvshow -> {
                    tablayout.gone()
                    fragmentActived = tvShow
                }
                R.id.menu_favorite -> {
                    tablayout.visible()
                    fragmentActived = favorite
                }
            }
            fragmentActived?.let { it1 -> fragmentShow(it1) }
            true
        }
        button_navigation.selectedItemId = R.id.menu_movie

    }

    override fun initViewModel(): MovieViewModel {
        return MovieViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_localization -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(): Toolbar = toolbar

    override fun getTabLayout(): TabLayout? = tablayout
}
