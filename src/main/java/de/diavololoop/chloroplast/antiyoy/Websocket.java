package de.diavololoop.chloroplast.antiyoy;

import de.diavololoop.chloroplast.antiyoy.util.Log;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Websocket extends WebSocketServer {

    private final AntiyoyOnline main;

    public Websocket(AntiyoyOnline main, int port) {
        super(new InetSocketAddress(port));
        this.main = main;
        start();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        main.netRemove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        main.netMessage(conn, message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        main.netRemove(conn);
    }

    @Override
    public void onStart() {
        Log.p(Log.CH_NET, "Websocket started");
    }
}
