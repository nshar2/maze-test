package uk.gov.dwp.maze;

import uk.gov.dwp.maze.exception.InvalidMazeException;
import uk.gov.dwp.maze.exception.MazeCreationException;
import uk.gov.dwp.maze.model.Explorer;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.model.MazeRestrictedView;

/**
 * Created by sharman on 01/02/2018.
 */
public class MazeApp {

    private Maze maze;

    public Explorer startMaze(String classpathFileName) {
        try {
            maze = MazeBuilder.buildMazeFromClasspathFile(classpathFileName);
            MazeRestrictedView mazeRestrictedView = new MazeRestrictedView(maze);
            Explorer explorer = new Explorer(mazeRestrictedView);

            explorer.dropInAtStartingPoint();  //the explorer has now been dropped at the starting point and is facing north

            return explorer;
        }
        catch (InvalidMazeException ime){
            throw ime;
        }
        catch (Exception e) {
            throw new MazeCreationException();
        }
    }

    public static void main(String[] args) {
        MazeApp mazeApp = new MazeApp();
        mazeApp.startMaze("Maze1.txt");
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }
}
