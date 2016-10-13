package com.android.msahakyan.ottonovaclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.msahakyan.ottonovaclient.adapter.delegates.AdapterDelegate;
import com.android.msahakyan.ottonovaclient.adapter.delegates.AdapterDelegatesManager;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.util.AppUtils;

import java.util.List;

/**
 * @author msahakyan
 */

public abstract class BaseListAdapter<T extends AdapterItem> extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<T>> mDelegatesManager;
    private List<T> mItems;

    protected BaseListAdapter(Context ctx, List<T> items) {
        mItems = items;
        mDelegatesManager = new AdapterDelegatesManager<>();
    }

    protected void setAdapterDelegates(List<AdapterDelegate<List<T>>> delegates) {
        mDelegatesManager.clear();
        for (AdapterDelegate<List<T>> delegate : delegates) {
            mDelegatesManager.addDelegate(delegate);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDelegatesManager.getItemViewType(mItems, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mDelegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mDelegatesManager.onBindViewHolder(mItems, position, holder);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<T> items) {
        if (items != null) {
            mItems.addAll(items);
            final int startIndex = Math.max(0, mItems.size() - items.size() - 1);
            notifyItemRangeChanged(startIndex, mItems.size());
        }
    }

    public void add(T item) {
        if (item != null) {
            mItems.add(item);
            notifyItemInserted(mItems.size() - 1);
        }
    }

    public void update(int position, T item) {
        if (position != RecyclerView.NO_POSITION && item != null) {
            mItems.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void remove(int position) {
        if (position != RecyclerView.NO_POSITION) {
            mItems.remove(position);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (!AppUtils.isEmpty(mItems)) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }
}
