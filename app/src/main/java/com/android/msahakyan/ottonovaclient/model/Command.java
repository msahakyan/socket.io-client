package com.android.msahakyan.ottonovaclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author msahakyan
 */

public class Command implements AdapterItem {

//    author: "nick-name", command: { type: "", data: {} }

    @SerializedName("author")
    private String author;

    @SerializedName("command")
    private ServerCommand command;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ServerCommand getCommand() {
        return command;
    }

    public void setCommand(ServerCommand command) {
        this.command = command;
    }
}
