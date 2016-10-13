package com.android.msahakyan.ottonovaclient.model.data;

import com.android.msahakyan.ottonovaclient.model.CommandData;

/**
 * @author msahakyan
 */

public class DateData extends CommandData {

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
