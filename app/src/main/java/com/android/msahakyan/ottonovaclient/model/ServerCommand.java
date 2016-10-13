package com.android.msahakyan.ottonovaclient.model;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author msahakyan
 */
public class ServerCommand {

    public static final String TYPE_COMPLETE = "complete";
    public static final String TYPE_RATE = "rate";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_MAP = "map";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_COMPLETE, TYPE_DATE, TYPE_RATE, TYPE_MAP})
    @interface CommandType {
    }

    @SerializedName("type")
    @CommandType
    private String type;

    @SerializedName("data")
    private CommandData data;

    @CommandType
    public String getType() {
        return type;
    }

    public void setType(@CommandType String type) {
        this.type = type;
    }

    public CommandData getData() {
        return data;
    }

    public void setData(CommandData data) {
        this.data = data;
    }
}
