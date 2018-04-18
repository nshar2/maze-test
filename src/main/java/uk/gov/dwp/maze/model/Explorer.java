package uk.gov.dwp.maze.model;

import uk.gov.dwp.maze.exception.IllegalMoveException;

import java.util.*;

public class Explorer {

    private MazeRestrictedView mazeRestrictedView;

    private MazeCell currentLocation;
    private Direction currentFacingDirection;

    private List<MazeCell> path = new ArrayList<>();

    public Explorer(MazeRestrictedView mazeRestrictedView) {
        this.mazeRestrictedView = mazeRestrictedView;
    }

    public void dropInAtStartingPoint() {
        currentFacingDirection = Direction.NORTH;
        currentLocation = mazeRestrictedView.getStartingPoint();
        path.add(currentLocation);
    }

    public void moveForward() {
        if (! canMoveForward()) {
            throw new IllegalMoveException();
        }

        currentLocation = mazeRestrictedView.getNeighbouringCell(currentLocation, currentFacingDirection).get();
        path.add(currentLocation);
    }

    private boolean canMoveForward() {
        return mazeRestrictedView.canMoveForward(currentLocation, currentFacingDirection);
    }

    public void turnLeft() {
        currentFacingDirection = Direction.evaluateDirectionOnTurnLeft(currentFacingDirection);
    }

    public void turnRight() {
        currentFacingDirection = Direction.evaluateDirectionOnTurnRight(currentFacingDirection);
    }

    public CellContent declareCellContentInFront() {
        return mazeRestrictedView.getCellContentInFront(currentLocation, currentFacingDirection);
    }

    public Set<Direction> listMovementOptions() {
        return mazeRestrictedView.getMovementOptions(currentLocation);
    }

    public List<MazeCell> reportRecordOfMovement() {
        return Collections.unmodifiableList(path);
    }

    public MazeCell getCurrentLocation() {
        return currentLocation;
    }

    public Direction getCurrentFacingDirection() {
        return currentFacingDirection;
    }
}
