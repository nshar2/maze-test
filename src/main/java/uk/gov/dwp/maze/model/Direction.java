package uk.gov.dwp.maze.model;

/**
 * Created by sharman on 01/02/2018.
 */
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Direction evaluateDirectionOnTurnLeft(Direction currentFacingDirection) {
        switch (currentFacingDirection){
            case EAST: return NORTH;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            case NORTH: return WEST;
        }

        return null;//will not happen
    }

    public static Direction evaluateDirectionOnTurnRight(Direction currentFacingDirection) {
        switch (currentFacingDirection){
            case EAST: return SOUTH;
            case WEST: return NORTH;
            case SOUTH: return WEST;
            case NORTH: return EAST;
        }

        return null;//will not happen
    }
}
