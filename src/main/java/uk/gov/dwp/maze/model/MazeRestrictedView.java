package uk.gov.dwp.maze.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by sharman on 01/02/2018.
 */
public class MazeRestrictedView {

    private Maze maze;

    public MazeRestrictedView(Maze maze) {
        this.maze = maze;
    }

    public MazeCell getStartingPoint() {
        return maze.getStartingPoint();
    }

    public boolean canMoveForward(MazeCell currentLocation, Direction currentFacingDirection) {

        Set<CellContent> cellContentAllowingEntry = new HashSet<>(Arrays.asList(CellContent.SPACE, CellContent.START, CellContent.FINISH));

        CellContent contentAtNeighbouringLocation = maze.getContentAtNeighbouringLocation(currentLocation.getRow(),
                currentLocation.getColumn(),
                currentFacingDirection);

        return cellContentAllowingEntry.contains(contentAtNeighbouringLocation);
    }

    public CellContent getCellContentInFront(MazeCell currentLocation, Direction currentFacingDirection) {
        return maze.getContentAtNeighbouringLocation(currentLocation.getRow(),
                currentLocation.getColumn(),
                currentFacingDirection);
    }

    public Set<Direction> getMovementOptions(MazeCell currentLocation) {
        return maze.getMovementOptions(currentLocation);
    }

    public Optional<MazeCell> getNeighbouringCell(MazeCell currentLocation, Direction currentFacingDirection) {
        return maze.getCellAtNeighbouringLocation(currentLocation.getRow(),
                currentLocation.getColumn(),
                currentFacingDirection);
    }
}
