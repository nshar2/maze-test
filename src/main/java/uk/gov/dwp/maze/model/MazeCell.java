package uk.gov.dwp.maze.model;

/**
 * Created by sharman on 01/02/2018.
 */
public class MazeCell {
    private final int row;
    private final int column;
    private final CellContent cellContent;

    public MazeCell(final int row, final int column, final CellContent cellContent) {
        this.row = row;
        this.column = column;
        this.cellContent = cellContent;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CellContent getCellContent() {
        return cellContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MazeCell mazeCell = (MazeCell) o;

        if (row != mazeCell.row) return false;
        if (column != mazeCell.column) return false;
        return cellContent == mazeCell.cellContent;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + (cellContent != null ? cellContent.hashCode() : 0);
        return result;
    }
}
