package de.diavololoop.chloroplast.antiyoy.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.diavololoop.chloroplast.antiyoy.util.Log;
import de.diavololoop.chloroplast.antiyoy.util.Security;

import java.util.Optional;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONBase {

    public final static String TYPE_SERVER_MESSAGE          = "server_message";
    public final static String TYPE_TEXT_MESSAGE            = "text_message";
    public final static String TYPE_PLAYER_JOIN             = "player_join";
    public final static String TYPE_PLAYER_INFO             = "player_info";
    public final static String TYPE_JOIN_LOBBY              = "join_lobby";
    public final static String TYPE_JOIN_PASSWORD_REQUEST   = "join_password_request";
    public final static String TYPE_WRONG_PASSWORD          = "wrong_password";
    public final static String TYPE_LOBBY                   = "lobby";
    public final static String TYPE_LOBBY_KICK              = "lobby_kick";
    public final static String TYPE_LOBBY_START             = "lobby_start";
    public final static String TYPE_LOBBY_LEAVE             = "lobby_leave";
    public final static String TYPE_MAP_REQUEST             = "map_request";
    public final static String TYPE_MAP_REQUEST_FAILED      = "map_request_failed";
    public final static String TYPE_GAME_MAP                = "game_map";
    public final static String TYPE_ACTION_MOVE             = "action_move";
    public final static String TYPE_ACTION_CREATE           = "action_create";
    public final static String TYPE_ACTION_END              = "action_end";

    public final static Gson gson = new Gson();

    public final String jsontype;

    public static void readJson(ClientInterface client, String json) {

        try {
            JSONBase base = gson.fromJson(json, JSONBase.class);

            switch (base.jsontype) {
                case TYPE_TEXT_MESSAGE: {
                    JSONTextMessage o = gson.fromJson(json, JSONTextMessage.class);
                    if (o.isValid()) {
                        client.receiveTextMessage(o.message);
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_PLAYER_JOIN: {
                    JSONPlayerJoin o = gson.fromJson(json, JSONPlayerJoin.class);
                    if (o.isValid()) {
                        client.receivePlayerJoin(o.name);
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_JOIN_LOBBY: {
                    JSONJoinLobby o = gson.fromJson(json, JSONJoinLobby.class);
                    if (o.isValid()) {

                        if (o.password == null) {
                            client.receiveJoinLobby(o.lobbyname, Optional.empty());
                        } else if (o.password.passwordType.equals(JSONJoinLobby.PASSWORD_TYPE_HASH)) {
                            client.receiveJoinLobby(o.lobbyname, Optional.of(o.password.password.toLowerCase()));
                        } else {
                            client.receiveJoinLobby(o.lobbyname, Optional.of(Security.sha512(o.password.password)));
                        }
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_LOBBY_KICK: {
                    JSONLobbyKick o = gson.fromJson(json, JSONLobbyKick.class);
                    if (o.isValid()) {
                        client.receiveLobbyKick(o.playerID);
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_LOBBY_LEAVE:
                    client.receiveLobbyLeave();
                    break;
                case TYPE_LOBBY_START:
                    client.receiveLobbyStart();
                    break;
                case TYPE_MAP_REQUEST: {
                    JSONMapRequest o = gson.fromJson(json, JSONMapRequest.class);
                    if (o.isValid()) {
                        client.receiveMapRequest(o.mapname);
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_ACTION_CREATE: {
                    JSONActionCreate o = gson.fromJson(json, JSONActionCreate.class);
                    if (o.isValid()) {
                        client.receiveActionCreate(o.posX, o.posY, JSONGameMap.typeOf(o.type));
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_ACTION_MOVE: {
                    JSONActionMove o = gson.fromJson(json, JSONActionMove.class);
                    if (o.isValid()) {
                        client.receiveActionMove(o.fromX, o.fromY, o.toX, o.toY);
                    } else {
                        Log.p(Log.CH_CLIENT, "received not valid json: "+json);
                    }
                    break;
                }
                case TYPE_ACTION_END: {
                    client.receiveActionEnd();
                    break;
                }
                default: throw new JsonSyntaxException("json type " + base.jsontype + " is not defined");

            }
        } catch (JsonSyntaxException e) {
            Log.p(Log.CH_CLIENT, "problem while parsing json: " + e.getMessage());
        }

    }

    protected JSONBase(String type) {
        jsontype = type;
    }

    public boolean isValid() {
        return true;
    }





}
