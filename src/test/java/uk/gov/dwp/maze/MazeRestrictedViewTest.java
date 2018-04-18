package uk.gov.dwp.maze;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.dwp.maze.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;

/**
 * Created by sharman on 01/02/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MazeRestrictedViewTest {

    @Mock
    private Maze maze;

    private MazeRestrictedView underTest;

    @Before
    public void setUp() {
        underTest = new MazeRestrictedView(maze);
    }

    @Test
    public void shouldGetStartingPoint() throws Exception {

        MazeCell mazeCell= new MazeCell(3,5, CellContent.START);
        Mockito.when(maze.getStartingPoint()).thenReturn(mazeCell);

        MazeCell startingPoint = underTest.getStartingPoint();
        assertEquals(mazeCell, startingPoint);

        Mockito.verify(maze).getStartingPoint();
    }

    @Test
    public void canMoveForward() throws Exception {

        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(maze.getContentAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH))).thenReturn(CellContent.SPACE);

        boolean canMoveForward = underTest.canMoveForward(mazeCell, Direction.NORTH);

        assertEquals(true, canMoveForward);

        Mockito.verify(maze).getContentAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH));
    }

    @Test
    public void shouldGetCellContentInFront() throws Exception {
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(maze.getContentAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH))).thenReturn(CellContent.SPACE);

        CellContent cellContentInFront = underTest.getCellContentInFront(mazeCell, Direction.NORTH);
        assertEquals(CellContent.SPACE, cellContentInFront);

        Mockito.verify(maze).getContentAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH));
    }

    @Test
    public void shouldGetMovementOptions() throws Exception {

        MazeCell mazeCell= new MazeCell(7,11,CellContent.START);

        Set<Direction> movementOptionsExpected = new HashSet<>(Arrays.asList(Direction.EAST, Direction.SOUTH));

        Mockito.when(maze.getMovementOptions(eq(mazeCell))).thenReturn(movementOptionsExpected);

        Set<Direction> movementOptionsActual = underTest.getMovementOptions(mazeCell);

        Assert.assertEquals(movementOptionsExpected, movementOptionsActual);

        Mockito.verify(maze).getMovementOptions(eq(mazeCell));
    }

    @Test
    public void shouldGetNeighbouringCell() throws Exception {

        MazeCell mazeCell = new MazeCell(3,5,CellContent.START);

        MazeCell mazeCellNext = new MazeCell(3,5,CellContent.SPACE);

        Mockito.when(maze.getCellAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH))).thenReturn(Optional.of(mazeCellNext));

        Optional<MazeCell> nextCell = underTest.getNeighbouringCell(mazeCell, Direction.NORTH);
        assertEquals(Optional.of(mazeCellNext), nextCell);

        Mockito.verify(maze).getCellAtNeighbouringLocation(eq(3), eq(5), eq(Direction.NORTH));
    }

}