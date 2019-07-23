package com.yeputra.moviecatalogue.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.adapter.VPagerAdapter
import com.yeputra.moviecatalogue.base.BaseFragment
import com.yeputra.moviecatalogue.base.ITabView
import com.yeputra.moviecatalogue.model.VPager
import com.yeputra.moviecatalogue.view.favorite.MovieFavoriteFm
import com.yeputra.moviecatalogue.view.favorite.TVShowFavoriteFm
import com.yeputra.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFm : BaseFragment<FavoriteViewModel>() {
    override fun initViewModel(): FavoriteViewModel =
            ViewModelProviders.of(this).get(FavoriteViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pages = ArrayList<VPager>()
        pages.add(VPager(getString(R.string.lbl_movie), MovieFavoriteFm()))
        pages.add(VPager(getString(R.string.lbl_tvshow), TVShowFavoriteFm()))

        viewpager.adapter = VPagerAdapter(pages, childFragmentManager)
        viewpager.overScrollMode = View.OVER_SCROLL_NEVER

        (activity as ITabView).getTabLayout()?.setupWithViewPager(viewpager)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_search).isVisible = false
    }
}