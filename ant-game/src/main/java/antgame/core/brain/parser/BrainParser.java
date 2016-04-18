package antgame.core.brain.parser;

import antgame.core.brain.Brain;
import antgame.core.brain.instruction.Condition;
import antgame.core.brain.instruction.Instruction;
import antgame.core.brain.instruction.SenseInstruction;

import java.text.ParseException;
import java.util.List;

/**
 * @author Sam Marsh
 */
public class BrainParser {

    private final List<String> lines;

    public BrainParser(List<String> lines) {
        this.lines = lines;
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

            switch (tokens[0]) {
                case "Sense": {
                    break;
                }
                case "Mark": {
                    break;
                }
                case "Unmark:": {
                    break;
                }
                case "PickUp": {
                    break;
                }
                case "Drop": {
                    break;
                }
                case "Turn": {
                    break;
                }
                case "Move": {
                    break;
                }
                case "Flip": {
                    break;
                }
                default: {
                    throw new ParseException("unrecognised instruction: " + tokens[0], num);
                }
            }
        }
        return null;
    }

    private void handleSense(String[] tokens, int line) throws ParseException {
        if (tokens.length != 5) {
            //TODO more informative error message
            throw new ParseException("invalid syntax", line);
        }

        SenseInstruction.Type type = SenseInstruction.Type.from(tokens[1], line);
        int state1 = parseInt(tokens[2], line);
        int state2 = parseInt(tokens[3], line);
        Condition condition = Condition.from(tokens[4], line);

    }

    private int parseInt(String token, int line) throws ParseException {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected integer, got " + token, line);
        }
    }

}
