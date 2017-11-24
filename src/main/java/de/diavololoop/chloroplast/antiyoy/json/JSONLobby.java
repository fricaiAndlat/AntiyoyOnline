package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

import java.util.List;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONLobby extends JSONBase {

    public final String lobbyname;
    public final String playerLeaderID;
    public final List<JSONPlayerInfo> players;

    public JSONLobby(String lobbyname, String playerLeaderID, List<JSONPlayerInfo> players) {
        super(JSONBase.TYPE_LOBBY);
        this.lobbyname = lobbyname;
        this.playerLeaderID = playerLeaderID;
        this.players = players;
    }

    @Override
    public boolean isValid() {
        return lobbyname != null
                && playerLeaderID != null
                && players != null
                && lobbyname.length() <= Settings.INSTANCE.LOBBY_NAME_MAX_LENGTH
                && lobbyname.matches(Settings.INSTANCE.LOBBY_NAME_ALLOWED_REGEX)
                && players.size() > 0
                && players.stream().anyMatch(p -> p.id.equals(playerLeaderID)) //is a player the lobby leader?
                && players.stream().allMatch(p -> p.isValid()); // all player names are valid
    }
}
