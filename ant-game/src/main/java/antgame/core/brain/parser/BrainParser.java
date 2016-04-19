package antgame.core.brain.parser;

import antgame.core.brain.Brain;
import antgame.core.brain.instruction.*;

import java.text.ParseException;
import java.util.*;

/**
 * @author Sam Marsh
 */
public class BrainParser {

    private final List<String> lines;
    private final Set<Instruction> insns;

    private BrainParser(List<String> lines) {
        this.lines = lines;
        this.insns = new HashSet<>();
    }

    private Brain parseInstruction() throws ParseException {
        int num = -1;
        if (lines.size() > 10000) {
            throw new ParseException("brain file has more than 10,000 lines: " + lines.size(), num);
        }

        return new Brain(parseInstruction(0));
    }

    private Instruction parseInstruction(int insn) throws ParseException {
        //if we've already parsed the instruction, return it
        Optional<Instruction> check = insns.parallelStream().filter(i -> i.getInstruction() == insn).findAny();
        if (check.isPresent()) {
            return check.get();
        }

        String line = lines.get(insn);

        int semicolonIndex = line.indexOf(';');
        if (semicolonIndex != -1)
            line = line.substring(0, semicolonIndex);

        String[] tokens = line.split(" ");

        if (tokens.length < 1) {
            throw new ParseException("expected instruction but got empty line", insn);
        }

        Instruction.Type type = Instruction.Type.parse(tokens[0], insn);

        Instruction instruction;

        switch (type) {
            case SENSE:
                instruction = parseSenseInstruction(tokens, insn);
                break;
            case MARK:
                instruction = parseMarkInstruction(tokens, insn);
                break;
            case UNMARK:
                instruction = parseUnmarkInstruction(tokens, insn);
                break;
            case PICK_UP:
                instruction = parsePickUpInstruction(tokens, insn);
                break;
            case DROP:
                instruction = parseDropInstruction(tokens, insn);
                break;
            case TURN:
                instruction = parseTurnInstruction(tokens, insn);
                break;
            case MOVE:
                instruction = parseMoveInstruction(tokens, insn);
                break;
            case FLIP:
                instruction = parseFlipInstruction(tokens, insn);
                break;
            default:
                throw new AssertionError("internal error: unhandled type in parser: " + type);
        }

        insns.add(instruction);
        return instruction;
    }

    private Instruction parseSenseInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 5) {
            //TODO more informative error message
            throw new ParseException("invalid syntax", insn);
        }

        SenseInstruction.Direction direction = SenseInstruction.Direction.from(tokens[1], insn);
        Instruction st1 = parseInstruction(parseInt(tokens[2], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[3], insn));

        //TODO how to represent condition better?
        Condition condition = Condition.from(tokens[4], insn);

        return new SenseInstruction(insn, direction, st1, st2, condition);
    }

    private Instruction parseMarkInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", insn);
        }

        int marker = parseInt(tokens[1], insn);

        if (marker < 0 || marker > 5) {
            throw new ParseException("marker must be in the range 0..5, was " + marker, insn);
        }

        Instruction st = parseInstruction(parseInt(tokens[2], insn));

        return new MarkInstruction(insn, marker, st);
    }

    private Instruction parseUnmarkInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", insn);
        }

        int marker = parseInt(tokens[1], insn);

        if (marker < 0 || marker > 5) {
            throw new ParseException("marker must be in the range 0..5, was " + marker, insn);
        }

        Instruction st = parseInstruction(parseInt(tokens[2], insn));

        return new UnmarkInstruction(insn, marker, st);
    }

    private Instruction parsePickUpInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", insn);
        }

        Instruction st1 = parseInstruction(parseInt(tokens[1], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[2], insn));

        return new PickUpInstruction(insn, st1, st2);
    }

    private Instruction parseDropInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 2) {
            throw new ParseException("invalid syntax", insn);
        }

        Instruction st = parseInstruction(parseInt(tokens[1], insn));

        return new DropInstruction(insn, st);
    }

    private Instruction parseTurnInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", insn);
        }

        TurnInstruction.Direction direction = TurnInstruction.Direction.from(tokens[1], insn);
        Instruction st = parseInstruction(parseInt(tokens[2], insn));

        return new TurnInstruction(insn, direction, st);
    }

    private Instruction parseMoveInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax", insn);
        }

        Instruction st1 = parseInstruction(parseInt(tokens[1], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[2], insn));

        return new MoveInstruction(insn, st1, st2);
    }

    private Instruction parseFlipInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 4) {
            throw new ParseException("invalid syntax", insn);
        }

        int n = parseInt(tokens[1], insn);
        Instruction st1 = parseInstruction(parseInt(tokens[2], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[3], insn));

        return new FlipInstruction(insn, n, st1, st2);
    }

    private int parseInt(String token, int line) throws ParseException {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected integer, got " + token, line);
        }
    }

    public static Brain parseInstruction(ArrayList<String> lines) throws ParseException {
        return new BrainParser(lines).parseInstruction();
    }

}
