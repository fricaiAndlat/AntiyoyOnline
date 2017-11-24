package de.diavololoop.chloroplast.antiyoy.json;

import java.util.List;

/**
 * Created by Chloroplast on 17.11.2017.
 */
public class JSONMapFile {

    public final List<List<JSONGameMap.JSONCell>> cellRows = null;
    public final int playerNumber = -1;
    public final List<JSONGameMap.JSONArea> areas = null;

    private JSONMapFile() {
    }


    public boolean isValid() {
        return playerNumber != -1
                && cellRows != null
                && areas != null
                && cellRows.size() > 0
                && cellRows.get(0).size() > 0
                && cellRows.stream().allMatch(r -> r.stream().allMatch(c -> c.isValid()))
                && areas.stream().allMatch(a -> a.isValid());
    }

}
