package com.yeputra.moviecatalogue.model;

import androidx.fragment.app.Fragment;


public class VPager {
    private String title;
    private Fragment fragment;

    public VPager(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }

}