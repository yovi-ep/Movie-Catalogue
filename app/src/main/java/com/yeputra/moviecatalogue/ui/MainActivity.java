package com.yeputra.moviecatalogue.ui;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.adapter.VPagerAdapter;
import com.yeputra.moviecatalogue.base.BaseActivity;
import com.yeputra.moviecatalogue.model.VPager;
import com.yeputra.moviecatalogue.presenter.DataPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity<DataPresenter> {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<VPager> pages = new ArrayList<>();
        pages.add(new VPager(getString(R.string.lbl_movie), new MovieFm()));
        pages.add(new VPager(getString(R.string.lbl_tvshow), new TVShowFm()));

        viewPager.setAdapter(new VPagerAdapter(pages, getSupportFragmentManager()));
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected DataPresenter initPresenter() {
        return new DataPresenter(this);
    }
}
