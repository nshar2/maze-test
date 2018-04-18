package uk.gov.dwp.maze.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sharman on 01/02/2018.
 */
public class Maze {

    private final int width;
    private final int height;
    private MazeCell[][] cells;

    public Maze(MazeCell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells[0].length;
    }

    public int getNumberOfWalls() {
        return getCellContentCount(CellContent.WALL);
    }

    public int getNumberOfSpaces() {
        return getCellContentCount(CellContent.SPACE);
    }

    public int getNumberOfStarts() {
        return getCellContentCount(CellContent.START);
    }

    public int getNumberOfFinishes() {
        return getCellContentCount(CellContent.FINISH);
    }

    private int getCellContentCount(CellContent cellContent) {
        return (int) Arrays.stream(cells).flatMap(x -> Arrays.stream(x)).filter(aCell -> aCell.getCellContent().equals(cellContent)).count();
    }

    public Optional<MazeCell> getCellAtLocation(int row, int column) {
        if(!isValidLocation(row, column)) {
            return Optional.empty(); //this means no cell at the location or in other words cell is outside maze
        }
        MazeCell mazeCell = cells[column][row];
        return Optional.of(mazeCell);
    }

    private boolean isValidLocation(int row, int column) {
        if(row < 0 || row > width - 1 || column < 0 || column > height - 1) {
            return false;
        }
        return true;
    }

    public CellContent getContentAtLocation(int row, int column) {
        return getCellAtLocation(row,column).map(cell -> cell.getCellContent()).orElse(CellContent.DOES_NOT_EXIST);
    }

    public Optional<MazeCell> getCellAtNeighbouringLocation(int row, int column, Direction facingDirection) {
        switch (facingDirection) {
            case NORTH: {
               return getCellAtLocation(row,column + 1);
            }
            case SOUTH: {
                return getCellAtLocation(row,column - 1);
            }
            case EAST: {
                return getCellAtLocation(row + 1,column);
            }
            case WEST: {
                return getCellAtLocation(row - 1,column);
            }
        }

        return Optional.empty(); // this will not happen
    }

    public CellContent getContentAtNeighbouringLocation(int row, int column, Direction facingDirection) {
        return getCellAtNeighbouringLocation(row,column, facingDirection).map(cell -> cell.getCellContent()).orElse(CellContent.DOES_NOT_EXIST);
    }

    public Map<Direction,Optional<MazeCell>> getNeighbouringCells(MazeCell mazeCell) {
        Map<Direction,Optional<MazeCell>> neighbouringCells = new HashMap<>();

        neighbouringCells.put(Direction.NORTH, getCellAtNeighbouringLocation(mazeCell.getRow(), mazeCell.getColumn(), Direction.NORTH));
        neighbouringCells.put(Direction.SOUTH, getCellAtNeighbouringLocation(mazeCell.getRow(), mazeCell.getColumn(), Direction.SOUTH));
        neighbouringCells.put(Direction.EAST, getCellAtNeighbouringLocation(mazeCell.getRow(), mazeCell.getColumn(), Direction.EAST));
        neighbouringCells.put(Direction.WEST, getCellAtNeighbouringLocation(mazeCell.getRow(), mazeCell.getColumn(), Direction.WEST));

        return neighbouringCells;
    }

    public Set<Direction> getMovementOptions(MazeCell mazeCell) {

        //whichever direction has a space content in mazeCell needs to be returned in the list of directions

        Map<Direction, Optional<MazeCell>> neighbouringCells = getNeighbouringCells(mazeCell);

        Set<CellContent> cellContentAllowingEntry = new HashSet<>(Arrays.asList(CellContent.SPACE, CellContent.START, CellContent.FINISH));

        return neighbouringCells.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPresent() && cellContentAllowingEntry.contains(entry.getValue().get().getCellContent()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());
    }

    public MazeCell getStartingPoint() {
        return Arrays.stream(cells).flatMap(x -> Arrays.stream(x)).filter(aCell -> aCell.getCellContent().equals(CellContent.START)).findFirst().get();
    }
}