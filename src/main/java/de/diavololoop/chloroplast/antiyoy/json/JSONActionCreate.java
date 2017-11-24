package de.diavololoop.chloroplast.antiyoy.json;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONActionCreate extends JSONBase {

    public final String playerID;
    public final int posX, posY;
    public final int type;


    public JSONActionCreate(String playerID, int posX, int posY, int type) {
        super(JSONBase.TYPE_ACTION_CREATE);
        this.playerID = playerID;
        this.posX = posX;
        this.posY = posY;
        this.type = type;
    }

    @Override
    public boolean isValid() {
        return playerID != null && posX >= -0 && posY >= 0 && JSONGameMap.CELL_TYPES.contains(type);
    }
}
