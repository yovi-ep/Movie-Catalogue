package com.yeputra.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.base.ITabView
import com.yeputra.moviecatalogue.utils.Constans.Companion.CHANGE_LOCAL
import com.yeputra.moviecatalogue.utils.Constans.Companion.INTENT_FRAGMENT
import com.yeputra.moviecatalogue.utils.fragmentReplace
import com.yeputra.moviecatalogue.utils.gone
import com.yeputra.moviecatalogue.utils.visible
import com.yeputra.moviecatalogue.view.menu.FavoriteFm
import com.yeputra.moviecatalogue.view.menu.MovieFm
import com.yeputra.moviecatalogue.view.menu.TVShowFm
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_tab.*


class MainActivity : BaseToolbarActivity<MovieViewModel>(), ITabView {
    private var pageContent: Fragment = MovieFm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movie = pageContent
        val tvShow = TVShowFm()
        val favorite = FavoriteFm()

        button_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_movie -> {
                    tablayout.gone()
                    pageContent = movie
                    fragmentReplace(R.id.main_container, movie)
                }
                R.id.menu_tvshow -> {
                    tablayout.gone()
                    pageContent = tvShow
                    fragmentReplace(R.id.main_container, tvShow)
                }
                R.id.menu_favorite -> {
                    tablayout.visible()
                    pageContent = favorite
                }
            }
            fragmentReplace(R.id.main_container, pageContent)
            true
        }

        restoreSaveInstanceState(savedInstanceState)
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            supportFragmentManager.getFragment(it, INTENT_FRAGMENT)?.let { it2 ->
                pageContent = it2
                fragmentReplace(R.id.main_container, pageContent)

                if (pageContent is FavoriteFm)
                    tablayout.visible()
                else
                    tablayout.gone()
            }
        } ?: run {
            button_navigation.selectedItemId = R.id.menu_movie
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, INTENT_FRAGMENT, pageContent)
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
            R.id.menu_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(): Toolbar = toolbar

    override fun getTabLayout(): TabLayout? = tablayout

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHANGE_LOCAL) {
            button_navigation.selectedItemId = R.id.menu_movie
        }
    }
}
