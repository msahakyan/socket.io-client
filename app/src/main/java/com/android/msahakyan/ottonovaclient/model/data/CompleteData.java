package com.android.msahakyan.ottonovaclient.model.data;

import com.android.msahakyan.ottonovaclient.model.CommandData;

/**
 * @author msahakyan
 */

public class CompleteData extends CommandData {

    public String[] getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String[] suggestions) {
        this.suggestions = suggestions;
    }

    private String[] suggestions;
}
