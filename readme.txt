Assumptions:
Assumed that a valid maze(in terms of elements in rows and columns) is the starting point. So I did not try to validate different
scenarios where invalid maze could have been provided

West ->  Left movement
East -> Right movement
North -> Up movement
South -> down movement


Maze file would be loaded from classpath was an assumption.

--------------------------------

Design Decisions:

Java 8 was used

I did not want to couple Explorer and the Maze. So introduced a Mediator class named MazeRestrictedView. This class retricts what an Explorer
can do or see on the Maze.

An Invalid MazeCell will be represented by Optional.empty

The CellContent type for a MazeCell location which doesn't belong to Maze will be DOES_NOT_EXIST

--------------------------------

What could have been better:
1) The rows and columns in the Maze are zero based. It would have been better if I had made it 1 based for the user of Maze.
For example, to get the first row and column of the Maze, presently row and column values of zero would need to be passed.

2) A unit test class for Maze would have been nice. Also there are few more edge scenarios which should have their unit/integration tests.

3) Maze manages the row and column number of each and every cell.
There was hardly any need for MazeCell to maintain row and column information. This is clearly duplication of information.

