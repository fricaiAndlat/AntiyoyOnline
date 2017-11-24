package de.diavololoop.chloroplast.antiyoy;

import de.diavololoop.chloroplast.antiyoy.game.Lobby;
import de.diavololoop.chloroplast.antiyoy.game.Player;
import de.diavololoop.chloroplast.antiyoy.util.Log;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Chloroplast on 21.11.2017.
 */
public class StructureProvider {

    private final HashMap<String, Lobby> lobbys = new HashMap<String, Lobby>();

    public synchronized Lobby joinLobbyByName(String lobbyname, Player player, Optional<String> password) throws PasswordNeeded, PasswordWrong {

        Lobby lobby = lobbys.get(lobbyname);

        if (lobby == null) {
            lobby = new Lobby(lobbyname, password);

            lobbys.put(lobbyname, lobby);

            Log.p(Log.CH_DEBUG, "create lobby "+lobbyname);
        } else {

            if (!password.isPresent() && lobby.isPasswordNeeded()) {
                throw new PasswordNeeded();
            }

            if (!lobby.isPasswordCorrect(password)) {
                throw new PasswordWrong();
            }
        }

        lobby.join(player);

        return lobby;
    }

    public synchronized boolean removeLobbyIfEmpty(Lobby lobby) {
        Lobby l = lobbys.remove(lobby.lobbyname);

        if (l == null) {
            throw new IllegalStateException("lobby is not in list, cannot be removed");
        }

        Log.p(Log.CH_DEBUG, "removed lobby " + lobby.lobbyname);

        return l != null;

    }

    public class PasswordNeeded extends Exception {}
    public class PasswordWrong extends Exception {}
}
