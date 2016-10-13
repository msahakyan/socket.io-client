package com.android.msahakyan.ottonovaclient.model.data;

import com.android.msahakyan.ottonovaclient.model.CommandData;

/**
 * @author msahakyan
 */

public class RateData extends CommandData {

    private int[] values;

    public int[] getRateValues() {
        return values;
    }

    public void setRateValues(int[] values) {
        this.values = values;
    }
}
