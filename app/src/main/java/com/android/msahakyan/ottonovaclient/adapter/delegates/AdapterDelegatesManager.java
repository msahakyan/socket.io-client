package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Manager class for all {@link AdapterDelegate}-s
 *
 * @author msahakyan
 */
public class AdapterDelegatesManager<T> {

    static final String TAG = AdapterDelegatesManager.class.getSimpleName();
    private static final int NO_TYPE = -1;

    private SparseArrayCompat<AdapterDelegate<T>> mDelegates;

    public AdapterDelegatesManager() {
        mDelegates = new SparseArrayCompat<>();
    }

    /**
     * Adds {@link AdapterDelegate<T>} to adapter delegates manager
     *
     * @param delegate
     * @return
     */
    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        int itemViewType = delegate.getItemViewType();
        if (mDelegates.get(itemViewType) == null) {
            mDelegates.put(itemViewType, delegate);
        } else {
            Timber.d("The delegate for viewType: %1$d has been already registered.", itemViewType);
        }
        return this;
    }

    /**
     * Returns either null or viewType for the given delegate
     *
     * @param items
     * @param position
     * @return
     */
    public int getItemViewType(@NonNull T items, int position) {
        AdapterDelegate<T> delegate = getDelegateForViewType(items, position);
        return delegate == null ? NO_TYPE : delegate.getItemViewType();
    }

    /**
     * Calls on creation of viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<T> delegate = mDelegates.get(viewType);
        if (delegate == null) {
            Timber.e("Couldn't find delegate for viewType: %1$d", viewType);
            return null;
        }
        return delegate.onCreateViewHolder(parent);
    }

    /**
     * Calls on bind of viewHolder
     *
     * @param items
     * @param position
     * @param viewHolder
     */
    public void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate<T> delegate = mDelegates.get(viewHolder.getItemViewType());
        if (delegate == null) {
            Timber.e("Couldn't find delegate for viewType: %1$d", viewHolder.getItemViewType());
            return;
        }
        delegate.onBindViewHolder(items, position, viewHolder);
    }

    /**
     * Returns AdapterDelegate for given viewType
     *
     * @param items
     * @param position
     * @return
     */
    private AdapterDelegate<T> getDelegateForViewType(@NonNull T items, int position) {
        final int size = mDelegates.size();
        for (int i = 0; i < size; i++) {
            AdapterDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForViewType(items, position)) {
                return delegate;
            }
        }
        return null;
    }

    public void clear() {
        mDelegates.clear();
    }
}