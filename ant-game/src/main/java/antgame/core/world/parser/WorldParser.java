package antgame.core.world.parser;

import antgame.core.world.Cell;
import antgame.core.world.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a user world file. The world file is made up of the following:
 * <ol>
 *     <li>The size of the world in the x-direction</li>
 *     <li>The size of the world in the y-direction</li>
 *     <li>y world-describing lines, each made up of the x tokens, separated by spaces. The tokens can be: </li>
 *     <li>
 *         <ol>
 *             <li># - describing a rock</li>
 *             <li>. - describing a clear cell</li>
 *             <li>+ - describing a red anthill cell</li>
 *             <li>- - describing a black anthill cell</li>
 *             <li>1-9 - describing a clear cell with a number of food particles</li>
 *         </ol>
 *     </li>
 * </ol>
 *
 * @author Sam Marsh
 */
public class WorldParser {

    /**
     * Parses a world from an ordered list of lines.
     *
     * @param lines the list of strings making up a user world specification
     * @param name the name of the world
     * @return a world with the representation described by the passed argument
     * @throws ParseException if the user world specification is invalid
     */
    private static World parse(List<String> lines, String name) throws ParseException {
        int sizex;
        int sizey;
        try {
            sizex = Integer.parseInt(lines.get(0));
        } catch (NumberFormatException nfe) {
            String line = lines.get(0);
            String cut = line.length() > 5 ? line.subSequence(0, 5) + "..." : line;
            throw new ParseException("expected world size in x direction, instead got " + cut, 0
            );
        }
        try {
            sizey = Integer.parseInt(lines.get(1));
        } catch (NumberFormatException nfe) {
            String line = lines.get(1);
            String cut = line.length() > 5 ? line.subSequence(0, 5) + "..." : line;
            throw new ParseException("expected world size in y direction, instead got " + cut, 1);
        }

        Cell[][] cells = new Cell[sizex][sizey];

        //delete the first two lines which give the size of the world
        lines.remove(0); lines.remove(0);
        int offset = 2;

        if (lines.size() != sizey) {
            throw new ParseException("expected " + sizey + " world lines, got " + lines.size(), 1);
        }

        for (int y = 0; y < sizey; ++y) {
            String line = lines.get(y).trim();
            String[] tokens = line.split("\\s");

            if (tokens.length != sizex) {
                throw new ParseException("row too short: expected " + sizex + ", got " + tokens.length, y + offset);
            }

            for (int x = 0; x < tokens.length; ++x) {
                if (tokens[x].length() != 1) {
                    throw new ParseException("no space between tokens", y);
                }
                char c = tokens[x].charAt(0);

                switch (c) {
                    case '#':
                        cells[x][y] = new Cell(Cell.Type.ROCK, x, y);
                        break;
                    case '.':
                        cells[x][y] = new Cell(Cell.Type.CLEAR, x, y);
                        break;
                    case '+':
                        cells[x][y] = new Cell(Cell.Type.ANTHILL_RED, x, y);
                        break;
                    case '-':
                        cells[x][y] = new Cell(Cell.Type.ANTHILL_BLACK, x, y);
                        break;
                    default:
                        if (c >= '1' && c <= '9') {
                            cells[x][y] = new Cell(Cell.Type.CLEAR, x, y);
                            cells[x][y].setFood(Character.getNumericValue(c));
                            break;
                        }
                        throw new ParseException("invalid token: " + c, y + offset);
                }
            }

        }
        return new World(cells, name);
    }

    /**
     * Parses a world file from a file.
     *
     * @param file the file to read the world from
     * @return a world representing the layout specification described in the file
     * @throws ParseException if the world specification in the file is invalid
     * @throws IOException if an I/O exception occurred in reading the file contents
     */
    public static World parse(File file) throws ParseException, IOException {
        return parse(new ArrayList<>(Files.readAllLines(file.toPath())), file.getName());
    }

}
