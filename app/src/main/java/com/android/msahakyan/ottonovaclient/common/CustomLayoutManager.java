package com.android.msahakyan.ottonovaclient.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author msahakyan
 */

public class CustomLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        // Customizing vertical scrollability
        return isScrollEnabled && super.canScrollVertically();
    }
}