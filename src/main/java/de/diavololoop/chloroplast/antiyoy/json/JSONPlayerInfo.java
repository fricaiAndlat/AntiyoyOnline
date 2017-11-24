package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONPlayerInfo extends JSONBase {

    public final String name;
    public final String id;
    public final String preferredColor;

    public JSONPlayerInfo(String name, String id, String preferredColor) {
        super(JSONBase.TYPE_PLAYER_INFO);
        this.name = name;
        this.id = id;
        this.preferredColor = preferredColor;
    }

    @Override
    public boolean isValid() {
        return name != null
                && id != null
                && preferredColor != null
                && name.matches(Settings.INSTANCE.PLAYER_NAME_ALLOWED_REGEX)
                && (Settings.INSTANCE.PLAYER_COLORS.contains(preferredColor) || preferredColor.matches("#[0-9a-fA-F]{6}"));
    }
}
