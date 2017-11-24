package de.diavololoop.chloroplast.antiyoy.json;

import de.diavololoop.chloroplast.antiyoy.game.MapCell;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONGameMap extends JSONBase {

    public final static int CELL_TYPE_NONE        = -1;
    public final static int CELL_TYPE_EMPTY       =  0;
    public final static int CELL_TYPE_HOME        =  1;
    public final static int CELL_TYPE_HOUSE       =  2;
    public final static int CELL_TYPE_TOWER_MK1   =  3;
    public final static int CELL_TYPE_TOWER_MK2   =  4;
    public final static int CELL_TYPE_TREE_T1     =  5;
    public final static int CELL_TYPE_TREE_T2     =  6;
    public final static int CELL_TYPE_WARRIOR_MK1 =  7;
    public final static int CELL_TYPE_WARRIOR_MK2 =  8;
    public final static int CELL_TYPE_WARRIOR_MK3 =  9;
    public final static int CELL_TYPE_WARRIOR_MK4 = 10;

    public final static List<Integer> CELL_TYPES = Arrays.asList(CELL_TYPE_NONE, CELL_TYPE_EMPTY, CELL_TYPE_HOME,
            CELL_TYPE_HOUSE, CELL_TYPE_TOWER_MK1, CELL_TYPE_TOWER_MK2, CELL_TYPE_TREE_T1, CELL_TYPE_TREE_T2,
            CELL_TYPE_WARRIOR_MK1, CELL_TYPE_WARRIOR_MK2, CELL_TYPE_WARRIOR_MK3, CELL_TYPE_WARRIOR_MK4);

    public static MapCell.Type typeOf(int type) {
        switch (type) {
            case CELL_TYPE_NONE: return MapCell.Type.NONE;
            case CELL_TYPE_EMPTY: return MapCell.Type.EMPTY;
            case CELL_TYPE_HOME: return MapCell.Type.HOME;
            case CELL_TYPE_HOUSE: return MapCell.Type.HOUSE;
            case CELL_TYPE_TOWER_MK1: return MapCell.Type.TOWER_MK1;
            case CELL_TYPE_TOWER_MK2: return MapCell.Type.TOWER_MK2;
            case CELL_TYPE_TREE_T1: return MapCell.Type.TREE_T1;
            case CELL_TYPE_TREE_T2: return MapCell.Type.TREE_T2;
            case CELL_TYPE_WARRIOR_MK1: return MapCell.Type.WARRIOR_MK1;
            case CELL_TYPE_WARRIOR_MK2: return MapCell.Type.WARRIOR_MK2;
            case CELL_TYPE_WARRIOR_MK3: return MapCell.Type.WARRIOR_MK3;
            case CELL_TYPE_WARRIOR_MK4: return MapCell.Type.WARRIOR_MK4;
            default: throw new IllegalArgumentException("map cell type "+type+" is not known");
        }
    }

    public static int type(MapCell.Type type) {

        switch (type) {

            case NONE: return CELL_TYPE_NONE;
            case EMPTY:return CELL_TYPE_EMPTY;
            case HOME:return CELL_TYPE_HOME;
            case HOUSE:return CELL_TYPE_HOUSE;
            case TOWER_MK1:return CELL_TYPE_TOWER_MK1;
            case TOWER_MK2:return CELL_TYPE_TOWER_MK2;
            case TREE_T1:return CELL_TYPE_TREE_T1;
            case TREE_T2:return CELL_TYPE_TREE_T2;
            case WARRIOR_MK1:return CELL_TYPE_WARRIOR_MK1;
            case WARRIOR_MK2:return CELL_TYPE_WARRIOR_MK2;
            case WARRIOR_MK3:return CELL_TYPE_WARRIOR_MK3;
            case WARRIOR_MK4:return CELL_TYPE_WARRIOR_MK4;
        }

        throw new IllegalArgumentException("type is not in switch-case statement");

    }

    public final Map<String, String> playerColors;
    public final List<List<JSONCell>> cellRows;
    public final List<String> queue;
    public final List<JSONArea> areas;

    public JSONGameMap(Map<String, String> playerColors, List<List<JSONCell>> cellRows, List<String> queue, List<JSONArea> areas) {
        super(JSONBase.TYPE_GAME_MAP);
        this.playerColors = playerColors;
        this.cellRows = cellRows;
        this.queue = queue;
        this.areas = areas;
    }

    @Override
    public boolean isValid() {
        return playerColors != null
                && cellRows != null
                && queue != null
                && areas != null
                && cellRows.size() > 0
                && cellRows.get(0).size() > 0
                && cellRows.stream().allMatch(r -> r.stream().allMatch(c -> c.isValid()))
                && queue.stream().allMatch(p -> playerColors.containsKey(p))
                && areas.stream().allMatch(a -> a.isValid())
                && areas.size() == playerColors.size();
    }

    class JSONCell {

        public final String ownerPlayerID;
        public final int type;

        public JSONCell(String owner, int type) {
            this.ownerPlayerID = owner;
            this.type = type;
        }

        public boolean isValid(){
            return playerColors.containsKey(ownerPlayerID)
                    && CELL_TYPES.contains(type);
        }

    }

    class JSONArea {
        public final int money;
        public final String ownerID;
        public final List<JSONCellPos> cells;

        public JSONArea(String ownerID, int money, List<JSONCellPos> cells) {
            this.money = money;
            this.cells = cells;
            this.ownerID = ownerID;
        }

        public boolean isValid() {
            return money > 0
                    && playerColors.containsKey(ownerID)
                    && cells != null
                    && cells.size() > 0
                    && cells.stream().allMatch(c -> c.isValid());
        }
    }

    private class JSONCellPos {

        public final int x;
        public final int y;

        public JSONCellPos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isValid() {
            return x >= 0 && y >= 0;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof JSONCellPos){
                JSONCellPos other = (JSONCellPos)obj;
                return other.x == x && other.y == y;
            }
            return false;
        }
    }

}
