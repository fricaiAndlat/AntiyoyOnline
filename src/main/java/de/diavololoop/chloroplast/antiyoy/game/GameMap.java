package de.diavololoop.chloroplast.antiyoy.game;

import de.diavololoop.chloroplast.antiyoy.json.JSONBase;
import de.diavololoop.chloroplast.antiyoy.json.JSONGameMap;
import de.diavololoop.chloroplast.antiyoy.json.JSONMapFile;

import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class GameMap {

    private final static String mapRootURL = "maps/";
   // private final static List<>


    public static Optional<GameMap> load(String mapName) {

        if (mapName.contains("/")) {
            return Optional.empty();
        }

        String mapURL = mapRootURL + mapName;

        try {

            String plainJSON = new String(Files.readAllBytes(new File(mapURL).toPath()), StandardCharsets.UTF_8);




        } catch (IOException e) {
            e.printStackTrace();
        }

        //JSONGameMap json = JSONBase.gson.fromJson(JSONMapFile
        return null;


    }

    public int getPlayerNumber(){
        return 0;// TODO
    }

    public class MapDescriptor {
        String mapName;
        int playerNumber;
    }

}
