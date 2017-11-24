package de.diavololoop.chloroplast.antiyoy;

import de.diavololoop.chloroplast.antiyoy.game.Lobby;
import de.diavololoop.chloroplast.antiyoy.game.Player;
import de.diavololoop.chloroplast.antiyoy.json.JSONBase;
import de.diavololoop.chloroplast.antiyoy.util.Hex;
import de.diavololoop.chloroplast.antiyoy.util.Log;
import org.java_websocket.WebSocket;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class AntiyoyOnline {

    private final Map<String, Player> players = new HashMap<>();
    private final Map<String, WebSocket> connections = new HashMap<>();
    private final Map<WebSocket, String> playerIDs = new HashMap<>();

    private final Map<String, Lobby> lobbys = new HashMap<>();

    public void netSend(Player player, JSONBase o){
        Log.p(Log.CH_ALL, "send JSON: "+JSONBase.gson.toJson(o));
        if (!o.isValid()) {
            throw new IllegalArgumentException("tried to send not valid JSON message "+JSONBase.gson.toJson(o));
        }

        WebSocket connection = connections.get(player.playerID);

        if (connection == null) {
            throw new IllegalStateException("player connection for player "+player.playerID+" does not exist");
        }

        connection.send(JSONBase.gson.toJson(o));
    }

    public void netMessage(WebSocket conn, String message) {
        Log.p(Log.CH_ALL, "received JSON: "+message);

        String playerID = playerIDs.get(conn);

        if (playerID == null) {
            Player player = new Player(this, getNextPlayerID());
            playerID = player.playerID;

            players.put(playerID, player);
            connections.put(playerID, conn);
            playerIDs.put(conn, playerID);

            Log.p(Log.CH_DEBUG, "new player connected with id "+playerID);
        }

        Player player = players.get(playerID);

        if (player == null) {
           throw new IllegalStateException("player with id="+playerID+" was not found");
        }

        try {
            JSONBase.readJson(player, message);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void netRemove(WebSocket conn) {
    }

    private String getNextPlayerID() {

        BigInteger id = BigInteger.ZERO;
        String idString;

        do {
            id = id.add(BigInteger.ONE);
            idString = Hex.toHex(id.toByteArray());
        } while (players.containsKey(idString));

        return idString;
    }


    public Player getPlayerByID(String playerID) {
        return players.get(playerID);
    }
}
