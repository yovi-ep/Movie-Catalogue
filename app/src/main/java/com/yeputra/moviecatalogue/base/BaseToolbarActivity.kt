package com.yeputra.moviecatalogue.base

import android.graphics.drawable.Drawable

import androidx.appcompat.widget.Toolbar

abstract class BaseToolbarActivity<viewmodel : IBaseViewModel> : BaseActivity<viewmodel>(), IToolbar {

    override fun onStart() {
        super.onStart()

        setSupportActionBar(setToolbar())

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(setToolbarTitle())
            supportActionBar?.setDisplayHomeAsUpEnabled(setButtonBack())
            supportActionBar?.setDisplayShowHomeEnabled(setToolbarIcon())
        }
        if (setButtonBack()) {
            setToolbar().setNavigationOnClickListener { finish() }
        }
    }

    fun setToolbarTitle(toolbarTitle: String) {
        supportActionBar?.title = toolbarTitle
    }

    protected fun setToolbarTitle(toolbarTitle: Int) {
        supportActionBar?.setTitle(toolbarTitle)
    }

    protected fun setToolbarSubTitle(toolbarTitle: String) {
        supportActionBar?.subtitle = toolbarTitle
    }

    protected fun setToolbarSubTitle(toolbarTitle: Int) = supportActionBar?.setSubtitle(toolbarTitle)

    protected fun setToolbarIcon(image: Int) = supportActionBar?.setIcon(image)

    protected fun setToolbarIcon(image: Drawable) = supportActionBar?.setIcon(image)

    override fun setButtonBack(): Boolean = false

    override fun setToolbarTitle(): Boolean = false

    override fun setToolbarSubTitle(): Boolean = false

    override fun setToolbarIcon(): Boolean = false

    protected abstract fun setToolbar(): Toolbar
}