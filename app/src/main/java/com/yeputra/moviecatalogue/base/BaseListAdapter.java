package com.yeputra.moviecatalogue.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<item> extends BaseAdapter {
    private List<item> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    public void addItem(item item) {
        items.add(item);
    }

    public void addItem(List<item> item) {
        items.addAll(item);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(initItemView(), parent, false);
        }
        binding(convertView, items.get(position));
        return convertView;
    }

    protected abstract int initItemView();

    protected abstract void binding(View view, item item);
}
