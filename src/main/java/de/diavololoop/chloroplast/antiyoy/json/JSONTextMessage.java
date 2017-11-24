package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONTextMessage extends JSONBase {

    public final String message;
    public final String playerID;

    public JSONTextMessage(String message, String playerID) {
        super(JSONBase.TYPE_TEXT_MESSAGE);
        this.message = message;
        this.playerID = playerID;
    }


    @Override
    public boolean isValid() {
        return message != null && playerID != null && message.length() < Settings.INSTANCE.TEXT_MESSAGE_MAX_LENGTH;
    }

}
