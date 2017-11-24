package de.diavololoop.chloroplast.antiyoy;

import de.diavololoop.chloroplast.antiyoy.util.Hex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Settings {

    public static Settings INSTANCE = new Settings();

    public final int TEXT_MESSAGE_MAX_LENGTH = 100;
    public final int PLAYER_NAME_MAX_LENGTH = 32;
    public final String PLAYER_NAME_ALLOWED_REGEX = "[a-zA-Z0-9 -_#.:,;]*";
    public final List<String> PLAYER_COLORS = Arrays.asList("green", "red", "blue", "yellow", "cyan", "violet", "orange", "#C71585", "#7FFF00", "#8B4513");
    public final int LOBBY_NAME_MAX_LENGTH = 64;
    public final String LOBBY_NAME_ALLOWED_REGEX = "[a-zA-Z0-9 -_#.:,;]*";

    private final static Random RAND = new Random();


    public static String getColorForIndex(int index) {
        if (index < INSTANCE.PLAYER_COLORS.size()) {
            return INSTANCE.PLAYER_COLORS.get(index);
        }

        byte[] randColor = new byte[3];
        RAND.nextBytes(randColor);

        return "#"+ Hex.toHex(randColor);
    }
}
