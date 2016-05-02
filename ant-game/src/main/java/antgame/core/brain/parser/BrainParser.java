package antgame.core.brain.parser;

import antgame.core.Colony;
import antgame.core.brain.Brain;
import antgame.core.brain.instruction.*;
import antgame.core.world.Marker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;

/**
 * Parses a list of instructions represented by strings, and returns an ant-brain finite state machine which acts as
 * specified by the instructions.
 *
 * To parse a {@link List} of strings into a {@link Brain}, use the static
 * {@link BrainParser#parse(Colony.Colour, File)} method. Alternatively, a brain can be parsed directly from a file
 * using {@link BrainParser#parse(Colony.Colour, File)}
 *
 * @author Sam Marsh
 */
public class BrainParser {

    private static final int MAXIMUM_NUM_LINES = 10000;

    //keep a list of the lines
    private final List<String> lines;

    //also track the already-parsed instructions
    private final Set<Instruction> insns;

    /**
     * Creates a new parser to read an ant-brain as specified by the lines in the given list.
     *
     * @param lines the lines representing instructions
     */
    private BrainParser(List<String> lines) {
        this.lines = lines;
        this.insns = new HashSet<>();
    }

    /**
     * The main method to read a brain from the list of instructions.
     *
     * @return a brain representing the finite-state machine specified by the instruction strings
     * @throws ParseException if the instructions are not well-formed
     */
    private Brain parse() throws ParseException {
        //ensure file is not too large
        if (lines.size() > MAXIMUM_NUM_LINES) {
            throw new ParseException(String.format(
                    "brain file has more than %d lines: %d", MAXIMUM_NUM_LINES, lines.size()
            ), 0);
        }

        //return a new brain with the initial instruction as instruction-0
        return new Brain(parseInstruction(0));
    }

    /**
     * This is a generally recursive method which parses an instruction line , and by proxy all instructions which this
     * instruction depends on.
     *
     * @param insn the instruction number to parse (i.e. index in the list)
     * @return an instruction as specified by the instruction string in the list
     * @throws ParseException if the instruction is not well-formed
     */
    private Instruction parseInstruction(int insn) throws ParseException {
        if (insn < 0 || insn >= lines.size()) {
            throw new ParseException("no such instruction", insn);
        }

        //if we've already parsed the instruction, return it
        Optional<Instruction> check = insns.parallelStream().filter(i -> i.getID() == insn).findAny();
        if (check.isPresent()) {
            return check.get();
        }

        //retrieve the specified instruction
        String line = lines.get(insn);

        //chop off the comment part
        int semicolonIndex = line.indexOf(';');
        if (semicolonIndex != -1)
            line = line.substring(0, semicolonIndex);

        //split into tokens by whitespace
        String[] tokens = line.split("\\s");

        //as per requirements, instruction n is on line n, so line cannot be empty
        if (tokens.length < 1) {
            throw new ParseException("expected instruction but got empty line", insn);
        }

        //get the instruction type from the first token (will throw an exception if not well-formed)
        Instruction.Type type = Instruction.Type.parse(tokens[0], insn);

        //will hold this instruction
        Instruction instruction;

        //parse based on the type
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

        return instruction;
    }

    private Instruction parseSenseInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 5 && tokens.length != 6) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        SenseInstruction.Direction direction = SenseInstruction.Direction.from(tokens[1], insn);

        Condition.Type type = Condition.Type.from(tokens[4], insn);
        Condition condition;


        if (type == Condition.Type.MARKER) {
            if (tokens.length != 6) {
                throw new ParseException("invalid syntax: incorrect number of arguments", insn);
            }
            int marker = parseInt(tokens[5], insn);
            if (marker < 0 || marker > 5) {
                throw new ParseException("marker must be in the range 0..5, was " + marker, insn);
            }
            condition = new Condition(new Marker(marker));
        } else {
            if (tokens.length != 5) {
                throw new ParseException("invalid syntax: incorrect number of arguments", insn);
            }
            condition = new Condition(type);
        }

        SenseInstruction ret = new SenseInstruction(insn, direction, condition);
        insns.add(ret);

        Instruction st1 = parseInstruction(parseInt(tokens[2], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[3], insn));
        ret.success(st1);
        ret.failure(st2);

        return ret;
    }

    private Instruction parseMarkInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        int marker = parseInt(tokens[1], insn);

        if (marker < 0 || marker > 5) {
            throw new ParseException("marker must be in the range 0..5, was " + marker, insn);
        }

        MarkInstruction ret = new MarkInstruction(insn, new Marker(marker));
        insns.add(ret);

        Instruction st = parseInstruction(parseInt(tokens[2], insn));
        ret.success(st);
        ret.failure(st);

        return ret;
    }

    private Instruction parseUnmarkInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        int marker = parseInt(tokens[1], insn);

        if (marker < 0 || marker > 5) {
            throw new ParseException("marker must be in the range 0..5, was " + marker, insn);
        }

        UnmarkInstruction ret = new UnmarkInstruction(insn, new Marker(marker));
        insns.add(ret);

        Instruction st = parseInstruction(parseInt(tokens[2], insn));
        ret.success(st);
        ret.failure(st);

        return ret;
    }

    private Instruction parsePickUpInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        Instruction ret = new PickUpInstruction(insn);
        insns.add(ret);

        Instruction st1 = parseInstruction(parseInt(tokens[1], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[2], insn));
        ret.success(st1);
        ret.failure(st2);

        return ret;
    }

    private Instruction parseDropInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 2) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        DropInstruction ret = new DropInstruction(insn);
        insns.add(ret);

        Instruction st = parseInstruction(parseInt(tokens[1], insn));
        ret.success(st);
        ret.failure(st);

        return ret;
    }

    private Instruction parseTurnInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        TurnInstruction.Direction direction = TurnInstruction.Direction.from(tokens[1], insn);

        TurnInstruction ret = new TurnInstruction(insn, direction);
        insns.add(ret);

        Instruction st = parseInstruction(parseInt(tokens[2], insn));
        ret.success(st);
        ret.failure(st);

        return ret;
    }

    private Instruction parseMoveInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        MoveInstruction ret = new MoveInstruction(insn);
        insns.add(ret);

        Instruction st1 = parseInstruction(parseInt(tokens[1], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[2], insn));
        ret.success(st1);
        ret.failure(st2);

        return ret;
    }

    private Instruction parseFlipInstruction(String[] tokens, int insn) throws ParseException {
        if (tokens.length != 4) {
            throw new ParseException("invalid syntax: incorrect number of arguments", insn);
        }

        int n = parseInt(tokens[1], insn);

        FlipInstruction ret = new FlipInstruction(insn, n);
        insns.add(ret);

        Instruction st1 = parseInstruction(parseInt(tokens[2], insn));
        Instruction st2 = parseInstruction(parseInt(tokens[3], insn));
        ret.success(st1);
        ret.failure(st2);

        return ret;
    }

    private int parseInt(String token, int line) throws ParseException {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            throw new ParseException("expected integer, got " + token, line);
        }
    }

    /**
     * Parses a brain from a file which contains an ant-brain specification.
     *
     * @param file the file where the ant-brain is described
     * @return an ant-brain representing the states described by the file
     * @throws ParseException if the ant brain is malformed in the file
     * @throws IOException if an error in reading the file is encountered
     */
    public static Brain parse(File file) throws ParseException, IOException {
        return parse(Files.readAllLines(file.toPath()));
    }

    /**
     * Parses a brain from a list of strings which represent an ant-brain specification.
     *
     * @param lines the lines describing the ant brain
     * @return an ant-brain representing the states described by the list of strings
     * @throws ParseException if the ant-brain is malformed in the list of strings
     */
    public static Brain parse(List<String> lines) throws ParseException {
        return new BrainParser(lines).parse();
    }

}
