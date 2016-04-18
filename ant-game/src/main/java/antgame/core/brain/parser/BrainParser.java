package antgame.core.brain.parser;

import antgame.core.brain.Brain;
import antgame.core.brain.instruction.Condition;
import antgame.core.brain.instruction.Instruction;
import antgame.core.brain.instruction.MarkInstruction;
import antgame.core.brain.instruction.SenseInstruction;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sam Marsh
 */
public class BrainParser {

    private final List<String> lines;
    private final Map<Integer, Instruction> map;

    public BrainParser(List<String> lines) {
        this.lines = lines;
        this.map = new HashMap<>();
    }

    public Brain parse() throws ParseException {
        int num = -1;
        if (lines.size() > 10000) {
            throw new ParseException("brain file has more than 10,000 lines: " + lines.size(), num);
        }

        for (String line : lines) {
            ++num;

            int commentIndex = line.indexOf(';');
            if (commentIndex != -1) {
                line = line.substring(0, commentIndex);
            }

            String[] tokens = line.split(" ");
            if (tokens.length < 1) {
                //empty line or comment line - ignore
                continue;
            }

            Instruction.Type type = Instruction.Type.parse(tokens[0], num);

            switch (type) {
                case SENSE:
                    map.put(num, handleSense(tokens, num));
                    break;
                case MARK:
                    map.put(num, handleMark(tokens, num));
                    break;
            }

        }
        return null;
    }

    private Instruction handleSense(String[] tokens, int line) throws ParseException {
        if (tokens.length != 5) {
            //TODO more informative error message
            throw new ParseException("invalid syntax", line);
        }

        SenseInstruction.Direction direction = SenseInstruction.Direction.from(tokens[1], line);
        int st1 = parseInt(tokens[2], line);
        int st2 = parseInt(tokens[3], line);
        Condition condition = Condition.from(tokens[4], line);

        return new SenseInstruction(line, direction, st1, st2, condition);
    }

    private Instruction handleMark(String[] tokens, int line) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", line);
        }

        int marker = parseInt(tokens[1], line);

        if (marker < 0 || marker > 5) {
            throw new ParseException("marker must be in the range 0..5, was " + marker, line);
        }

        int st = parseInt(tokens[2], line);

        return new MarkInstruction(line, marker, st);
    }

    private int parseInt(String token, int line) throws ParseException {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected integer, got " + token, line);
        }
    }

}
