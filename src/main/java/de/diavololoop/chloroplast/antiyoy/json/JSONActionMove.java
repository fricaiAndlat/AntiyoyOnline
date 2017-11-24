package de.diavololoop.chloroplast.antiyoy.json;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONActionMove extends JSONBase {

    public final String playerID;
    public final int fromX, fromY;
    public final int toX, toY;

    public JSONActionMove(String playerID, int fromX, int fromY, int toX, int toY) {
        super(JSONBase.TYPE_ACTION_MOVE);
        this.playerID = playerID;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public boolean isValid() {
        return playerID != null
                && (fromX != toX || fromY != toY)
                && fromX >= 0 && fromY >= 0 && toX >= 0 && toY >= 0;
    }
}
