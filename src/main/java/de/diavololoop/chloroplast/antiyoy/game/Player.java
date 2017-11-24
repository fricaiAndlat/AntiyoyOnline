package de.diavololoop.chloroplast.antiyoy.game;

import de.diavololoop.chloroplast.antiyoy.AntiyoyOnline;
import de.diavololoop.chloroplast.antiyoy.Settings;
import de.diavololoop.chloroplast.antiyoy.StructureProvider;
import de.diavololoop.chloroplast.antiyoy.json.*;
import de.diavololoop.chloroplast.antiyoy.util.Log;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Player implements ClientInterface {

    private String name;
    public final String playerID;

    private final AntiyoyOnline main;

    private Optional<Lobby> currentLobby = Optional.empty();
    private Optional<Game> currentGame = Optional.empty();


    public Player(AntiyoyOnline main, String playerID){
        this.main = main;
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player)obj;
            return other.playerID.equals(playerID);
        } else {
            return false;
        }
    }

    @Override
    public synchronized void receiveTextMessage(String message) {

    }

    @Override
    public synchronized void receivePlayerJoin(String name) {
        if(this.name != null){
            Log.p(Log.CH_CLIENT, "received player_join from "+playerID+" another time. Thats not right");
        }

        this.name = name;

        sendPlayerInfo(name, this.playerID, Settings.INSTANCE.PLAYER_COLORS.get(0));
    }

    @Override
    public synchronized void receiveJoinLobby(String lobbyname, Optional<String> password) {

        if(currentLobby.isPresent()){
           Log.p(Log.CH_CLIENT, "cant join in a lobby while not leaving the old one player " + playerID);
           return;
        }


        try {

            Lobby lobby = Lobby.create(lobbyname, this, password);
            currentLobby = Optional.of(lobby);

        } catch (StructureProvider.PasswordNeeded e) {
            sendJoinPasswordRequest();
        } catch (StructureProvider.PasswordWrong e) {
            sendWrongPassword();
        }
    }

    @Override
    public synchronized void receiveLobbyKick(String playerID) {

        if (!currentLobby.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant kick member when not in lobby, received from "+this.playerID);
            return;
        }

        if (!currentLobby.get().isOwner(this)) {
            Log.p(Log.CH_CLIENT, "cant kick member when not owner, received from "+this.playerID);
            return;
        }

        if (playerID.equals(this.playerID)) {
            Log.p(Log.CH_CLIENT, "cant kick self "+this.playerID);
            return;
        }

        currentLobby.get().kick(playerID);
    }

    @Override
    public synchronized void receiveLobbyStart() {

        if (!currentLobby.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant start game not in lobby yet, received from "+this.playerID);
            return;
        }

        if (!currentLobby.get().isOwner(this)) {
            Log.p(Log.CH_CLIENT, "cant start game when not owner of lobby, received from "+this.playerID);
            return;
        }

        currentLobby.get().startGame();

    }

    @Override
    public synchronized void receiveMapRequest(String mapName) {

        if (!currentLobby.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant request a map while not in lobby yet, received from "+this.playerID);
            return;
        }

        if (!currentLobby.get().isOwner(this)) {
            Log.p(Log.CH_CLIENT, "cant request a map when not owner of lobby, received from "+this.playerID);
            return;
        }

        Optional<GameMap> map = GameMap.load(mapName);
        if (!map.isPresent() || map.get().getPlayerNumber() != currentLobby.get().getPlayerNumber()) {
            sendMapRequestFailed();
            return;
        }
        currentLobby.get().startMap(map.get());
    }

    @Override
    public synchronized void receiveActionEnd() {
        if (!currentGame.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant end a turn when not in a game "+this.playerID);
            return;
        }

        if (!currentGame.get().isPlayerTurn(this)) {
            Log.p(Log.CH_CLIENT, "it has to be the players turn to end a turn "+this.playerID);
            return;
        }

        currentGame.get().endPlayerTurn(this);
    }

    @Override
    public synchronized void receiveActionCreate(int posX, int posY, MapCell.Type type) {
        if (!currentGame.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant create an entity when not in a game "+this.playerID);
            return;
        }

        if (!currentGame.get().isPlayerTurn(this)) {
            Log.p(Log.CH_CLIENT, "cant create an entity while its not the players turn "+this.playerID);
            return;
        }

        currentGame.get().createEntity(posX, posY, type, this);
    }

    @Override
    public synchronized void receiveActionMove(int fromX, int fromY, int toX, int toY) {
        if (!currentGame.isPresent()) {
            Log.p(Log.CH_CLIENT, "cant move an entity when not in a game "+this.playerID);
            return;
        }

        if (!currentGame.get().isPlayerTurn(this)) {
            Log.p(Log.CH_CLIENT, "cant move an entity while its not the players turn "+this.playerID);
            return;
        }

        currentGame.get().moveEntity(fromX, fromY, toX, toY, this);
    }

    @Override
    public synchronized void receiveLobbyLeave() {

        if (!currentLobby.isPresent()) {
            Log.p(Log.CH_CLIENT, "user "+playerID+" leaved lobby while not in one");
        }
        currentGame.ifPresent(g -> g.leave(this));
        currentLobby.ifPresent(l -> l.leave(this));

        currentGame = Optional.empty();
        currentLobby = Optional.empty();

    }

    public void sendTextMessage(Player sender, String message) {
        main.netSend(this, new JSONTextMessage(message, sender.playerID));
    }

    public void sendServerMessage(String message) {
        main.netSend(this, new JSONServerMessage(message));
    }

    private void sendPlayerInfo(String name, String playerID, String preferedColor) {
        main.netSend(this, new JSONPlayerInfo(name, playerID, preferedColor));
    }

    public void sendJoinPasswordRequest() {
        main.netSend(this, new JSONJoinPasswordRequest());
    }

    public void sendWrongPassword() {
        main.netSend(this, new JSONWrongPassword());
    }

    public void sendLobby(Lobby lobby) {
        main.netSend(this, new JSONLobby(lobby.lobbyname, lobby.getOwnerID(),
                lobby.getMember().stream().map(m -> new JSONPlayerInfo(m.getName(), m.playerID, lobby.getColorFor(m))).collect(Collectors.toList())));
    }

    public void sendLobbyStart() {
        main.netSend(this, new JSONLobbyStart());
    }

    public void sendLobbyKick() {
        main.netSend(this, new JSONLobbyKick(playerID));

    }

    public void sendMapRequestFailed() {
        main.netSend(this, new JSONMapRequestFailed());
    }

    public void sendGameMap(GameMap map) {

    }

    public synchronized void sendActionMove(Player player, int fromX, int fromY, int toX, int toY) {
        main.netSend(this, new JSONActionMove(player.playerID, fromX, fromY, toX, toY));
    }
    public synchronized void sendActionCreate(Player player, int posX, int posY, MapCell.Type type) {
        main.netSend(this, new JSONActionCreate(player.playerID, posX, posY, JSONGameMap.type(type)));
    }

    public synchronized void sendActionEnd(Player player) {
        main.netSend(this, new JSONActionEnd());
    }

}
