package uk.gov.dwp.maze;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.dwp.maze.exception.IllegalMoveException;
import uk.gov.dwp.maze.model.*;

import java.util.*;

import static org.mockito.Matchers.eq;

/**
 * Created by sharman on 01/02/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExplorerTest {

    @Mock
    private MazeRestrictedView mazeRestrictedView;

    private Explorer underTest;

    @Before
    public void setUp() {
        underTest = new Explorer(mazeRestrictedView);
    }

    @Test
    public void shouldDropInAtStartingPoint() {
        MazeCell mazeCell= new MazeCell(3,5, CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        //assert
        Assert.assertEquals(Direction.NORTH, underTest.getCurrentFacingDirection());
        Assert.assertEquals(mazeCell, underTest.getCurrentLocation());

        Mockito.verify(mazeRestrictedView).getStartingPoint();
    }

    @Test
    public void shouldMoveForward() {

        //drop in at starting point
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        Mockito.when(mazeRestrictedView.canMoveForward(eq(mazeCell),eq(Direction.NORTH))).thenReturn(true);

        MazeCell mazeCellNext = new MazeCell(3,6,CellContent.SPACE);
        Mockito.when(mazeRestrictedView.getNeighbouringCell(eq(mazeCell),eq(Direction.NORTH))).thenReturn(Optional.of(mazeCellNext));

        //move forward
        underTest.moveForward();

        //assert moved
        Assert.assertEquals(Direction.NORTH, underTest.getCurrentFacingDirection());
        Assert.assertEquals(mazeCellNext, underTest.getCurrentLocation());

        Mockito.verify(mazeRestrictedView).canMoveForward(eq(mazeCell),eq(Direction.NORTH));
        Mockito.verify(mazeRestrictedView).getNeighbouringCell(eq(mazeCell),eq(Direction.NORTH));
    }

    @Test(expected = IllegalMoveException.class)
    public void shouldFailToMoveForward() {

        //drop in at starting point
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        Mockito.when(mazeRestrictedView.canMoveForward(eq(mazeCell),eq(Direction.NORTH))).thenReturn(false);

        //move forward
        underTest.moveForward();
    }

    @Test
    public void shouldTurnLeft() {
        //drop in at starting point
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        underTest.turnLeft();

        Assert.assertEquals(Direction.WEST, underTest.getCurrentFacingDirection());
        Mockito.verify(mazeRestrictedView).getStartingPoint();
    }

    @Test
    public void shouldTurnRight() {
        //drop in at starting point
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        underTest.turnRight();

        Assert.assertEquals(Direction.EAST, underTest.getCurrentFacingDirection());
        Mockito.verify(mazeRestrictedView).getStartingPoint();
    }

    @Test
    public void shouldDeclareCellContentInFront() {

        //drop in at starting point
        MazeCell mazeCell= new MazeCell(7,11,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        Mockito.when(mazeRestrictedView.getCellContentInFront(eq(mazeCell),eq(Direction.NORTH))).thenReturn(CellContent.WALL);

        CellContent cellContentInFront = underTest.declareCellContentInFront();

        Assert.assertEquals(CellContent.WALL, cellContentInFront);
        Mockito.verify(mazeRestrictedView).getStartingPoint();
        Mockito.verify(mazeRestrictedView).getCellContentInFront(eq(mazeCell),eq(Direction.NORTH));
    }

    @Test
    public void shouldListMovementOptions() {
        //drop in at starting point
        MazeCell mazeCell= new MazeCell(7,11,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        Set<Direction> movementOptionsExpected = new HashSet<>(Arrays.asList(Direction.EAST, Direction.SOUTH));

        Mockito.when(mazeRestrictedView.getMovementOptions(eq(mazeCell))).thenReturn(movementOptionsExpected);

        Set<Direction> movementOptionsActual = underTest.listMovementOptions();

        Assert.assertEquals(movementOptionsExpected, movementOptionsActual);

        Mockito.verify(mazeRestrictedView).getStartingPoint();
        Mockito.verify(mazeRestrictedView).getMovementOptions(eq(mazeCell));
    }

    @Test
    public void shouldReportRecordOfMovement() {
        //drop in at starting point
        MazeCell mazeCell= new MazeCell(3,5,CellContent.START);
        Mockito.when(mazeRestrictedView.getStartingPoint()).thenReturn(mazeCell);
        underTest.dropInAtStartingPoint();

        Mockito.when(mazeRestrictedView.canMoveForward(eq(mazeCell),eq(Direction.NORTH))).thenReturn(true);

        MazeCell mazeCellNext = new MazeCell(3,6,CellContent.SPACE);
        Mockito.when(mazeRestrictedView.getNeighbouringCell(eq(mazeCell),eq(Direction.NORTH))).thenReturn(Optional.of(mazeCellNext));

        //move forward
        underTest.moveForward();

        List<MazeCell> recordOfMovement = underTest.reportRecordOfMovement();

        Assert.assertEquals(mazeCell, recordOfMovement.get(0));
        Assert.assertEquals(mazeCellNext, recordOfMovement.get(1));

        Mockito.verify(mazeRestrictedView).canMoveForward(eq(mazeCell),eq(Direction.NORTH));
        Mockito.verify(mazeRestrictedView).getNeighbouringCell(eq(mazeCell),eq(Direction.NORTH));
    }

}