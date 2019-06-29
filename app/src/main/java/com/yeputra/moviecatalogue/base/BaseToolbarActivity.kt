package com.yeputra.moviecatalogue.base

import androidx.appcompat.widget.Toolbar

interface IToolbar {
    fun setToolbarTitle(): Boolean

    fun setButtonBack(): Boolean

    fun setToolbarIcon(): Boolean
}

abstract class BaseToolbarActivity<viewmodel : IBaseViewModel> : BaseActivity<viewmodel>(), IToolbar {

    protected abstract fun setToolbar(): Toolbar

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

    override fun setToolbarTitle(): Boolean = false

    override fun setButtonBack(): Boolean = false

    override fun setToolbarIcon(): Boolean = false
}