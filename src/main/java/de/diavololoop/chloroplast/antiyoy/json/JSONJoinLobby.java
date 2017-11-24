package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.Settings;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONJoinLobby extends JSONBase {


    public final static String PASSWORD_TYPE_PLAIN = "plain";
    public final static String PASSWORD_TYPE_HASH = "hash";

    private final static String PASSWORD_HASH_REGEX = "[0-9a-fA-F]{128}";

    /* actual object stuff */

    public final String lobbyname = null;
    public final JSONPassword password = null;

    private JSONJoinLobby() {
        super(JSONBase.TYPE_JOIN_LOBBY);
    }

    @Override
    public boolean isValid() {
        return lobbyname != null
                && lobbyname.length() <= Settings.INSTANCE.LOBBY_NAME_MAX_LENGTH
                && lobbyname.matches(Settings.INSTANCE.LOBBY_NAME_ALLOWED_REGEX)
                && (password == null || (password.password != null && password.passwordType != null  && password.isValid()));
    }

    public class JSONPassword {

        public final String password = null;
        public final String passwordType = null;

        private boolean isValid(){
            return (passwordType.equals(PASSWORD_TYPE_HASH) && password.matches(PASSWORD_HASH_REGEX)) || passwordType.equals(PASSWORD_TYPE_PLAIN);
        }

    }
}
