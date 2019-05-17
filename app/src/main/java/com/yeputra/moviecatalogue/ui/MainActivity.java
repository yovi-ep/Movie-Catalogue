package com.yeputra.moviecatalogue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.adapter.VPagerAdapter;
import com.yeputra.moviecatalogue.base.BaseToolbarActivity;
import com.yeputra.moviecatalogue.model.VPager;
import com.yeputra.moviecatalogue.presenter.DataPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseToolbarActivity<DataPresenter> {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_localization: {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Toolbar setToolbar() {
        return findViewById(R.id.toolbar);
    }
}
