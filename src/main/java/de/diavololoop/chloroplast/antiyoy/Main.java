package de.diavololoop.chloroplast.antiyoy;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {


        AntiyoyOnline antiyoy = new AntiyoyOnline();
        Websocket websocket = new Websocket(antiyoy, 81);

        WebSocketClient client1 = new WebSocketClient(new URI("ws://localhost:81/")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {}

            @Override
            public void onMessage(String message) {}

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.err.println("client closed code="+code+" reason="+reason+" remote= "+remote);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("client closed");
                ex.printStackTrace();
            }
        };

        WebSocketClient client2 = new WebSocketClient(new URI("ws://localhost:81/")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {}

            @Override
            public void onMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.err.println("client closed code="+code+" reason="+reason+" remote= "+remote);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("client closed");
                ex.printStackTrace();
            }
        };

        client1.connectBlocking();
        client2.connectBlocking();


        client1.send("{'jsontype': 'player_join', 'name': 'Chloroplast'}");
        client1.send("{'jsontype': 'join_lobby', 'lobbyname': 'testlobby1'}");
        client1.send("{'jsontype': 'lobby_leave'}");

        printInfo("player mitochond");

        client2.send("{'jsontype': 'player_join', 'name': 'Mitochond'}");
        client2.send("{'jsontype': 'join_lobby', 'lobbyname': 'testlobby2', 'password': {'password': 'pw123', 'passwordType': 'plain'}}");
        client2.send("{'jsontype': 'lobby_leave'}");

        printInfo("join in same lobby without password");

        client1.send("{'jsontype': 'join_lobby', 'lobbyname': 'none'}");
        client2.send("{'jsontype': 'join_lobby', 'lobbyname': 'none'}");

        client1.send("{'jsontype': 'lobby_leave'}");
        client2.send("{'jsontype': 'lobby_leave'}");

        printInfo("join in same lobby with plain password (correct)");

        client2.send("{'jsontype': 'join_lobby', 'lobbyname': 'plainlobby', 'password': {'password': 'pw123', 'passwordType': 'plain'}}");
        client1.send("{'jsontype': 'join_lobby', 'lobbyname': 'plainlobby', 'password': {'password': 'pw123', 'passwordType': 'plain'}}");

        Thread.sleep(100);

        client1.send("{'jsontype': 'lobby_leave'}");
        client2.send("{'jsontype': 'lobby_leave'}");

        printInfo("join in same lobby with hash password (correct)");


        client1.send("{'jsontype': 'join_lobby', 'lobbyname': 'hashlobby', 'password': {'password': 'aa5a1976568caa1b89f67459a1a6289884c7af87d3d36b32f44d61358555a0c2e5006e9fcdffb356a3423615a34bf9f2a42950d00ae1ad9d72d37a240745bd18', 'passwordType': 'hash'}}");
        client2.send("{'jsontype': 'join_lobby', 'lobbyname': 'hashlobby', 'password': {'password': 'AA5A1976568CAA1B89F67459A1A6289884C7AF87D3D36B32F44D61358555A0C2E5006E9FCDFFB356A3423615A34BF9F2A42950D00AE1AD9D72D37A240745BD18', 'passwordType': 'hash'}}");

        Thread.sleep(100);

        client1.send("{'jsontype': 'lobby_leave'}");
        client2.send("{'jsontype': 'lobby_leave'}");

        client1.close();
        client2.close();


    }

    private static void printInfo(String m) throws InterruptedException {
        Thread.sleep(500);
        System.out.println();
        System.out.println(m);
        System.out.println();
        Thread.sleep(500);
    }

}
