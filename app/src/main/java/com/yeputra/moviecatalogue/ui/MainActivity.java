package com.yeputra.moviecatalogue.ui;

import android.os.Bundle;

import com.yeputra.moviecatalogue.base.BaseActivity;
import com.yeputra.moviecatalogue.R;
import com.yeputra.moviecatalogue.presenter.DataPresenter;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseActivity<DataPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NotNull
    @Override
    public DataPresenter initPresenter() {
        return new DataPresenter(this);
    }
}
