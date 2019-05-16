package com.yeputra.moviecatalogue.base;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T>
        extends RecyclerView.Adapter<VH>
        implements Filterable, FilterResultListener<T> {

    private List<T> items;

    protected boolean isSearching = false;

    protected abstract void onBindViewHolder(VH holder, T item, int position);

    public BaseRecyclerViewAdapter() {
        this.items = new ArrayList<>();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public Filter.FilterResults performFiltering(CharSequence constraint) {
                isSearching = true;
                String charString = constraint.toString();
                Filter.FilterResults filterResults = new Filter.FilterResults();

                items = onFilterResult(charString);

                filterResults.values = items;
                filterResults.count = getItemCount();
                return filterResults;
            }

            @Override
            public void publishResults(CharSequence constraint, Filter.FilterResults results) {
                items = (List<T>) results.values;
                notifyDataSetChanged();
                isSearching = false;
            }
        };
    }

    @Override
    public List<T> onFilterResult(String query) {
        return this.items;
    }

    @Override
    public int getItemCount() {
        return this.items == null ? 0 : this.items.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position){
        onBindViewHolder(holder, this.items.get(position), position);
    }

    public T getItem(int position) { return this.items.get(position); }

    public void addItem(T item) {
        this.items.add(item);
        this.notifyDataSetChanged();
    }

    public void appendItem(List<T> items){
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    public void replaceItem(List<T> items){
        this.items.clear();
        this.appendItem(items);
        this.notifyDataSetChanged();
    }
}