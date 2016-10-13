package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.util.AppUtils;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author msahakyan
 */

public abstract class BaseAdapterDelegate implements AdapterDelegate<List<AdapterItem>> {

    static final int TYPE_MESSAGE = 0;
    static final int TYPE_COMMAND_COMPLETE = 1;
    static final int TYPE_COMMAND_RATE = 2;
    static final int TYPE_COMMAND_DATE = 3;
    static final int TYPE_COMMAND_MAP = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_MESSAGE, TYPE_COMMAND_COMPLETE, TYPE_COMMAND_RATE, TYPE_COMMAND_DATE, TYPE_COMMAND_MAP})
    @interface ElementViewType {
    }

    private int viewType;
    private Context context;
    protected ICommandCommunicationListener listener;

    protected BaseAdapterDelegate(Context ctx, @ElementViewType int viewType, ICommandCommunicationListener listener) {
        context = ctx;
        this.viewType = viewType;
        this.listener = listener;
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        return !AppUtils.isEmpty(items);
    }

    @Override
    @ElementViewType
    public int getItemViewType() {
        return viewType;
    }

    protected Context getContext() {
        return context;
    }
}
