package com.yeputra.moviecatalogue.base;

import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.Toolbar;

public abstract class BaseToolbarActivity<presenter extends IBasePresenter>
        extends BaseActivity<presenter>
        implements IToolbar {

    @Override
    protected void onStart() {
        super.onStart();

        setSupportActionBar(setToolbar());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(setToolbarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(setButtonBack());
            getSupportActionBar().setDisplayShowHomeEnabled(setToolbarIcon());
        }
        if(setButtonBack()){
            setToolbar().setNavigationOnClickListener(v -> finish());
        }
    }

    protected void setToolbarTitle(String toolbarTitle) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(toolbarTitle);
    }

    protected void setToolbarTitle(int toolbarTitle) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(toolbarTitle);
    }

    protected void setToolbarSubTitle(String toolbarTitle) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(toolbarTitle);
    }

    protected void setToolbarSubTitle(int toolbarTitle) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(toolbarTitle);
    }

    protected void setToolbarIcon(int image) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setIcon(image);
    }

    protected void setToolbarIcon(Drawable image) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setIcon(image);
    }

    @Override
    public boolean setButtonBack() {
        return false;
    }

    @Override
    public boolean setToolbarTitle() {
        return false;
    }

    @Override
    public boolean setToolbarSubTitle() {
        return false;
    }

    @Override
    public boolean setToolbarIcon() {
        return false;
    }

    protected abstract Toolbar setToolbar();
}