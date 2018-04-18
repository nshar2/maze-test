package uk.gov.dwp.maze;

import uk.gov.dwp.maze.exception.InvalidMazeException;
import uk.gov.dwp.maze.model.CellContent;
import uk.gov.dwp.maze.model.Maze;
import uk.gov.dwp.maze.model.MazeCell;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by sharman on 01/02/2018.
 */
public class MazeBuilder {

    public static Maze buildMazeFromClasspathFile(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(MazeBuilder.class.getClassLoader().getResource(fileName).toURI());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<MazeCell[]> mazeCellList = Files.lines(path).map(aLine -> mapLineToMazeRow(aLine, atomicInteger.getAndIncrement())).collect(Collectors.toList());

        MazeCell[][] cells = new MazeCell[atomicInteger.get()][];
        cells = mazeCellList.toArray(cells);

        Maze maze = new Maze(cells);

        validateMaze(maze);

        return maze;
    }

    private static void validateMaze(Maze maze) {
        if(maze.getNumberOfFinishes() != 1 || maze.getNumberOfStarts() != 1) {
            throw new InvalidMazeException();
        }
    }

    private static MazeCell[] mapLineToMazeRow(String aLine, int lineIndex) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<MazeCell> mazeCellRow = Arrays.stream(aLine.split(""))
                .map(aCharacter -> new MazeCell(atomicInteger.getAndIncrement(), lineIndex, CellContent.getCellContentForMazeCharacter(aCharacter)))
                .collect(Collectors.toList());

        MazeCell[] MazeCellArr = new MazeCell[atomicInteger.get()];
        return mazeCellRow.toArray(MazeCellArr);
    }
}
