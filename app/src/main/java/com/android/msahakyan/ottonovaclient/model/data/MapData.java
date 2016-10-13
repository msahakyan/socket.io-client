package com.android.msahakyan.ottonovaclient.model.data;

import com.android.msahakyan.ottonovaclient.model.CommandData;
import com.google.gson.annotations.SerializedName;

/**
 * @author msahakyan
 */

public class MapData extends CommandData {

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
