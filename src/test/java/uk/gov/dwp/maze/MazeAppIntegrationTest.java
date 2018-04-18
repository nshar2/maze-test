package uk.gov.dwp.maze;

import org.junit.Test;
import uk.gov.dwp.maze.exception.InvalidMazeException;
import uk.gov.dwp.maze.exception.MazeCreationException;
import uk.gov.dwp.maze.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by sharman on 02/02/2018.
 */
public class MazeAppIntegrationTest {

    @Test
    public void shouldStartMazeAndDropExplorerAtStart() throws Exception {
        MazeApp underTest = new MazeApp();
        Explorer explorer = underTest.startMaze("valid-maze.txt");

        assertEquals(Direction.NORTH, explorer.getCurrentFacingDirection());

        assertEquals(3, explorer.getCurrentLocation().getRow());
        assertEquals(3, explorer.getCurrentLocation().getColumn());
        assertEquals(CellContent.START, explorer.getCurrentLocation().getCellContent());

        assertMazeStatistics(underTest.getMaze());
    }

    private void assertMazeStatistics(Maze maze) {
        assertEquals(74, maze.getNumberOfSpaces());
        assertEquals(149, maze.getNumberOfWalls());
    }

    @Test
    public void shouldTellCellContents() throws Exception {
        MazeApp mazeApp = new MazeApp();
        mazeApp.startMaze("valid-maze.txt");
        Maze maze = mazeApp.getMaze();

        CellContent contentAtLocation = maze.getContentAtLocation(1, 14);
        assertEquals(CellContent.FINISH, contentAtLocation);
    }

    @Test
    public void shouldMoveForward() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");
        explorer.turnRight();
        explorer.moveForward();
        assertEquals(4, explorer.getCurrentLocation().getRow());
        assertEquals(3, explorer.getCurrentLocation().getColumn());
        assertEquals(CellContent.SPACE, explorer.getCurrentLocation().getCellContent());
    }

    @Test
    public void shouldTurnLeft() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");
        explorer.turnLeft();
        assertEquals(3, explorer.getCurrentLocation().getRow());
        assertEquals(3, explorer.getCurrentLocation().getColumn());
        assertEquals(Direction.WEST, explorer.getCurrentFacingDirection());
    }

    @Test
    public void shouldTurnRight() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");
        explorer.turnRight();
        assertEquals(3, explorer.getCurrentLocation().getRow());
        assertEquals(3, explorer.getCurrentLocation().getColumn());
        assertEquals(Direction.EAST, explorer.getCurrentFacingDirection());
    }

    @Test
    public void shouldDeclareMovementOptions() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");

        Set<Direction> movementOptionsExpected = new HashSet<>(Arrays.asList(Direction.EAST));
        Set<Direction> movementOptionsActual = explorer.listMovementOptions();

        assertEquals(movementOptionsExpected, movementOptionsActual);
    }

    @Test
    public void shouldDeclareWhatIsInFront() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");
        explorer.turnRight();
        explorer.moveForward();
        explorer.turnLeft();
        CellContent cellContentInFront = explorer.declareCellContentInFront();
        assertEquals(CellContent.WALL, cellContentInFront);
    }

    @Test
    public void shouldReportRecordOfMovement() throws Exception {
        MazeApp mazeApp = new MazeApp();
        Explorer explorer = mazeApp.startMaze("valid-maze.txt");
        explorer.turnRight();
        explorer.moveForward();
        explorer.turnLeft();
        explorer.turnLeft();
        explorer.moveForward();
        explorer.turnRight();
        explorer.turnRight();
        explorer.moveForward();

        List<MazeCell> mazeCellsVisited = explorer.reportRecordOfMovement();

        assertEquals(4, mazeCellsVisited.size());
        assertEquals(4, mazeCellsVisited.get(1).getRow());
        assertEquals(3, mazeCellsVisited.get(2).getRow());
        assertEquals(4, mazeCellsVisited.get(3).getRow());
    }

    @Test(expected = MazeCreationException.class)
    public void shouldNotStartMaze_invalidCellContent() throws Exception {
        MazeApp underTest = new MazeApp();
        Explorer explorer = underTest.startMaze("invalid-maze.txt");
    }

    @Test(expected = InvalidMazeException.class)
    public void shouldNotStartMaze_invalidNumberOfStarts() throws Exception {
        MazeApp underTest = new MazeApp();
        Explorer explorer = underTest.startMaze("invalid-starts-maze.txt");
    }
}