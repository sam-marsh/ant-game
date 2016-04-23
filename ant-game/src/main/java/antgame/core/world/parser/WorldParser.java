package antgame.core.world.parser;

import antgame.core.world.Cell;
import antgame.core.world.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;

/**
 * @author Sam Marsh
 */
public class WorldParser {

    public static World parse(List<String> lines) throws ParseException {
        int sizex;
        int sizey;
        try {
            sizex = Integer.parseInt(lines.get(0));
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected world size in x direction, instead got " + lines.get(0), 0);
        }
        try {
            sizey = Integer.parseInt(lines.get(1));
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected world size in y direction, instead got " + lines.get(1), 1);
        }

        Cell[][] cells = new Cell[sizex][sizey];

        lines.remove(0); lines.remove(1);
        int offset = 2;

        if (lines.size() != sizey) {
            throw new ParseException("expected " + sizey + " world lines, got " + lines.size(), 1);
        }

        for (int y = 0; y < sizey; ++y) {
            String line = lines.get(y);
            String[] tokens = line.split(" ");

            if (tokens.length != sizex) {
                throw new ParseException("row too short", y + offset);
            }

            for (int x = 0; x < tokens.length; ++x) {
                if (tokens[x].length() != 1) {
                    throw new ParseException("no space between tokens", y);
                }
                char c = tokens[x].charAt(0);

                switch (c) {
                    case '#':
                        cells[x][y] = new Cell(Cell.Type.ROCK);
                        break;
                    case '.':
                        cells[x][y] = new Cell(Cell.Type.CLEAR);
                        break;
                    case '+':
                        cells[x][y] = new Cell(Cell.Type.ANTHILL_RED);
                        break;
                    case '-':
                        cells[x][y] = new Cell(Cell.Type.ANTHILL_BLACK);
                        break;
                    default:
                        if (c >= '1' && c <= '9') {
                            cells[x][y] = new Cell(Cell.Type.CLEAR);
                            cells[x][y].setFood(Character.getNumericValue(c));
                            break;
                        }
                        throw new ParseException("invalid token: " + c, y + offset);
                }
            }

        }
        return new World(cells);
    }

    public static World parse(File file) throws ParseException, IOException {
        return parse(Files.readAllLines(file.toPath()));
    }

}
