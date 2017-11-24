package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONServerMessage extends JSONBase{

    public final String message;

    public JSONServerMessage(String message) {
        super(JSONBase.TYPE_SERVER_MESSAGE);
        this.message = message;
    }

    @Override
    public boolean isValid() {
        return message != null;
    }
}
