package com.android.msahakyan.ottonovaclient.network;

import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.ServerCommand;
import com.android.msahakyan.ottonovaclient.model.data.CompleteData;
import com.android.msahakyan.ottonovaclient.model.data.DateData;
import com.android.msahakyan.ottonovaclient.model.data.MapData;
import com.android.msahakyan.ottonovaclient.model.data.RateData;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import static com.android.msahakyan.ottonovaclient.model.ServerCommand.TYPE_COMPLETE;
import static com.android.msahakyan.ottonovaclient.model.ServerCommand.TYPE_DATE;
import static com.android.msahakyan.ottonovaclient.model.ServerCommand.TYPE_MAP;
import static com.android.msahakyan.ottonovaclient.model.ServerCommand.TYPE_RATE;

/**
 * @author msahakyan
 */
public class CommandDataDeserializer implements JsonDeserializer<Command> {

    private static final String KEY_COMMAND = "command";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";
    private static final String KEY_LATITUDE = "lat";
    private static final String KEY_LONGITUDE = "lng";


    @Override
    public Command deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
        throws JsonParseException {

        JsonObject jsonObject = ((JsonObject) jsonElement).getAsJsonObject(KEY_COMMAND);

        String author = ((JsonObject) jsonElement).get(KEY_AUTHOR).getAsString();
        String commandType = jsonObject.get(KEY_TYPE).getAsString();

        Command command = new Command();
        command.setAuthor(author);
        switch (commandType) {
            case TYPE_DATE:
                deserializeAsDate(jsonObject, command);
                break;
            case TYPE_RATE:
                deserializeAsRate(jsonObject, command);
                break;
            case TYPE_COMPLETE:
                deserializeAsComplete(jsonObject, command);
                break;
            case TYPE_MAP:
                deserializeAsMap(jsonObject, command);
                break;
            default:
                throw new IllegalArgumentException("Command type: " + commandType + " is not supported!");
        }

        return command;
    }

    private void deserializeAsComplete(JsonObject jsonObject, Command command) {
        ServerCommand serverCommand = new ServerCommand();
        serverCommand.setType(TYPE_COMPLETE);

        JsonArray jsonArray = jsonObject.get(KEY_DATA).getAsJsonArray();
        String[] suggestions = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            suggestions[i] = jsonArray.get(i).getAsString();
        }

        CompleteData completeData = new CompleteData();
        completeData.setSuggestions(suggestions);
        serverCommand.setData(completeData);
        command.setCommand(serverCommand);
    }

    private void deserializeAsMap(JsonObject jsonObject, Command command) {
        ServerCommand serverCommand = new ServerCommand();
        serverCommand.setType(TYPE_MAP);

        //{ author: "nick-name", command: { type: "", data: {lat: "", lng: ""}}}
        JsonObject locationObj = jsonObject.get(KEY_DATA).getAsJsonObject();

        MapData mapData = new MapData();
        mapData.setLatitude(locationObj.get(KEY_LATITUDE).getAsString());
        mapData.setLongitude(locationObj.get(KEY_LONGITUDE).getAsString());

        serverCommand.setData(mapData);
        command.setCommand(serverCommand);
    }

    private void deserializeAsRate(JsonObject jsonObject, Command command) {
        ServerCommand serverCommand = new ServerCommand();
        serverCommand.setType(TYPE_RATE);

        JsonArray jsonArray = jsonObject.get(KEY_DATA).getAsJsonArray();
        int[] rate = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            rate[i] = jsonArray.get(i).getAsInt();
        }

        RateData rateData = new RateData();
        rateData.setRateValues(rate);
        serverCommand.setData(rateData);
        command.setCommand(serverCommand);
    }

    private void deserializeAsDate(JsonObject jsonObject, Command command) {
        ServerCommand serverCommand = new ServerCommand();
        serverCommand.setType(TYPE_DATE);

        DateData dateData = new DateData();
        dateData.setTimestamp(jsonObject.get(KEY_DATA).getAsString());
        serverCommand.setData(dateData);
        command.setCommand(serverCommand);
    }
}