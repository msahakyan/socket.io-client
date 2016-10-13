package com.android.msahakyan.ottonovaclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author msahakyan
 */

public class Message implements AdapterItem {

    @SerializedName("author")
    private String author;

    @SerializedName("message")
    private String message;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
