package de.diavololoop.chloroplast.antiyoy.json;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONLobbyKick extends JSONBase {

    public final String playerID;

    public JSONLobbyKick(String playerID) {
        super(JSONBase.TYPE_LOBBY_KICK);
        this.playerID = playerID;
    }

    @Override
    public boolean isValid() {
        return playerID != null;
    }
}
