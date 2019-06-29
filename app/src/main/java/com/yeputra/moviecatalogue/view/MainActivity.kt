package com.yeputra.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.VPagerAdapter
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.model.VPager
import com.yeputra.moviecatalogue.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_tab.*
import java.util.*


class MainActivity : BaseToolbarActivity<MovieViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pages = ArrayList<VPager>()
        pages.add(VPager(getString(R.string.lbl_movie), MovieFm()))
        pages.add(VPager(getString(R.string.lbl_tvshow), TVShowFm()))

        viewpager.adapter = VPagerAdapter(pages, supportFragmentManager)
        viewpager.overScrollMode = View.OVER_SCROLL_NEVER

        tablayout.setupWithViewPager(viewpager)
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

    override fun setToolbar(): Toolbar {
        return findViewById(R.id.toolbar)
    }
}
