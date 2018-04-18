package uk.gov.dwp.maze.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sharman on 01/02/2018.
 */
public enum CellContent {
    WALL("X"),
    SPACE(" "),
    START("S"),
    FINISH("F"),
    DOES_NOT_EXIST("D");

    CellContent(String mazeCharacter) {
        Holder.MAP.put(mazeCharacter, this);
    }

    private static class Holder {
        static Map<String, CellContent> MAP = new HashMap<>();
    }

    public static CellContent getCellContentForMazeCharacter(String mazeCharacter) {
        CellContent cellContent = Holder.MAP.get(mazeCharacter);
            if(cellContent == null) {
                throw new IllegalStateException(String.format("Unsupported type %s.", mazeCharacter));
            }
        return cellContent;
    }
}
