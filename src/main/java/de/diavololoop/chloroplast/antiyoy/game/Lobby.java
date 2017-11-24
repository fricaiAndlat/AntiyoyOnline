package de.diavololoop.chloroplast.antiyoy.game;

import de.diavololoop.chloroplast.antiyoy.Settings;
import de.diavololoop.chloroplast.antiyoy.StructureProvider;
import de.diavololoop.chloroplast.antiyoy.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Lobby {

    private final static StructureProvider PROV = new StructureProvider();

    public final String lobbyname;

    private final List<Player> players = new LinkedList<Player>();
    private Player owner;

    private Optional<String> password = Optional.empty();

    public Lobby(String lobbyname, Optional<String> password) {
        this.lobbyname = lobbyname;
        this.password = password;
    }

    public static Lobby create(String name, Player player, Optional<String> password) throws StructureProvider.PasswordNeeded, StructureProvider.PasswordWrong {
        return PROV.joinLobbyByName(name, player, password);
    }

    public synchronized boolean isPasswordCorrect(Optional<String> password) {


        if(this.password.isPresent()){

            if (password.isPresent() && password.get().equals(this.password.get())) {
               return true;
            }

            return false;
        }

        return true;
    }

    public boolean isEmpty() {
        return owner == null;
    }

    public synchronized void setPassword(Optional<String> password){
        this.password = password;
    }

    public boolean isPasswordNeeded() {
        return password.isPresent();
    }

    public boolean isOwner(Player player) {
        return player.playerID.equals(this.owner.playerID);
    }

    public synchronized void join(Player player) {

        Log.p(Log.CH_DEBUG, "player " + player.playerID + " join channel " + this.lobbyname);

        if(players.size() == 0){
            owner = player;
        }

        players.add(player);

        if (owner == null) {
            owner = player;
        }

        notifyMembers();

    }

    public synchronized  void notifyMembers() {
        players.forEach(p -> p.sendLobby(this));

    }

    public synchronized void kick(String playerID) {
        if (owner.playerID.equals(playerID)) {
            throw new IllegalArgumentException("The owner of a game cant kick himself");
        }

        boolean removed = players.removeIf(p -> p.playerID.equals(playerID));



        if (!removed) {
            Log.p(Log.CH_CLIENT, "kicked player was not found in lobby");
        }
    }

    public synchronized void startGame() {
        // TODO
    }

    public int getPlayerNumber() {
        return players.size();
    }

    public synchronized void startMap(GameMap map) {
        // TODO
    }

    public synchronized void leave(Player player) {
        boolean removed = players.removeIf(p -> p.playerID.equals(player.playerID));


        if (!removed) {
            Log.p(Log.CH_CLIENT, "leaved player was not found in lobby");
            return;
        }

        Log.p(Log.CH_DEBUG, "player " + player.playerID + " leave channel " + this.lobbyname);



        if (players.size() == 0) {
            PROV.removeLobbyIfEmpty(this);
            return;
        } else {
            Log.p(Log.CH_DEBUG, "keeping not empty lobby "+this.lobbyname);
        }

        if (owner.equals(player)) {
            Log.p(Log.CH_DEBUG, "player "+player.playerID+" is no longer lobbyleader of lobby "+this.lobbyname+", now its "+players.get(0).playerID);
            owner = players.get(0);
        }
        notifyMembers();
    }

    public String getOwnerID() {
        return owner.playerID;
    }

    public List<Player> getMember() {
        return players;
    }

    public synchronized String getColorFor(Player m) {
        int index = players.indexOf(m);

        if (index < 0) {
            throw new IllegalArgumentException("player "+m.playerID+" is not in list");
        }

        return Settings.getColorForIndex(index);
    }
}
