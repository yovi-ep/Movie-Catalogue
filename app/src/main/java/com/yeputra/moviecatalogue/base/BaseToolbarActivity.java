package com.yeputra.moviecatalogue.base;

import android.widget.TextView;

import java.util.Objects;

import androidx.appcompat.widget.Toolbar;

public abstract class BaseToolbarActivity
        extends BaseActivity
        implements IToolbar {

    private TextView toolbarTitle;

    protected void setToolbar(int idToolbar, int idTitleToolbar) {
        setToolbar(idToolbar);
        toolbarTitle = findViewById(idTitleToolbar);
    }

    protected void setToolbar(int idToolbar) {
        Toolbar toolbar = findViewById(idToolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(setButtonBack());
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            if(setButtonBack()){
                toolbar.setNavigationOnClickListener(v -> finish());
            }
        }
    }

    protected void setToolbarTitle(String toolbarTitle) {
        if(this.toolbarTitle != null)
            this.toolbarTitle.setText(toolbarTitle);
    }

    protected void setToolbarTitle(int toolbarTitle) {
        if(this.toolbarTitle != null)
            this.toolbarTitle.setText(toolbarTitle);
    }


    @Override
    public boolean setButtonBack() { return false; }
}
