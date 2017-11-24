package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.game.MapCell;

import java.util.Optional;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public interface ClientInterface {

    public void receiveTextMessage(String message);
    public void receivePlayerJoin(String name);
    public void receiveJoinLobby(String lobbyname, Optional<String> password);
    public void receiveLobbyKick(String playerID);
    public void receiveLobbyStart();
    public void receiveMapRequest(String mapName);
    public void receiveActionEnd();
    public void receiveActionCreate(int posX, int posY, MapCell.Type type);
    public void receiveActionMove(int fromX, int fromY, int toX, int toY);
    public void receiveLobbyLeave();
}
