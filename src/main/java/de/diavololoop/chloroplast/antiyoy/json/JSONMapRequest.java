package de.diavololoop.chloroplast.antiyoy.json;

/**
 * Created by Chloroplast on 16.11.2017.
 */
public class JSONMapRequest extends JSONBase {

    public final String mapname = null;

    private JSONMapRequest() {
        super(JSONBase.TYPE_MAP_REQUEST);
    }

    @Override
    public boolean isValid() {
        return mapname != null;
    }
}
