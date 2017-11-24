package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONPlayerJoin extends JSONBase {


    public final String name = null;

    private JSONPlayerJoin() {
        super(JSONBase.TYPE_PLAYER_JOIN);
    }

    @Override
    public boolean isValid() {
        return name != null
                && name.length() <= Settings.INSTANCE.PLAYER_NAME_MAX_LENGTH
                && name.matches(Settings.INSTANCE.PLAYER_NAME_ALLOWED_REGEX);
    }
}
