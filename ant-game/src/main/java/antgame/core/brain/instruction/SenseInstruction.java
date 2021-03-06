package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents an instruction to perform a sense operation in a particular direction. Ants can sense whether
 * particular {@link Condition}s hold for cells specified by a {@link Direction}. An instance of this class
 * is an order for an ant to 'ask a question' to the world about a cell.
 *
 * @author Sam Marsh
 */
public class SenseInstruction extends Instruction {

    //the direction to sense in
    private final Direction direction;

    //the 'question' to ask the world about the cell
    private final Condition condition;

    /**
     * Creates a new sense instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param direction the direction in which to sense
     * @param condition the 'question' to ask the world about the cell
     */
    public SenseInstruction(int insn, Direction direction, Condition condition) {
        super(insn, Type.SENSE);
        this.direction = direction;
        this.condition = condition;
    }

    /**
     * @return the direction in which to sense
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return the condition to sense on the cell
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Represents a possible sense direction.
     */
    public enum Direction {

        HERE("Here"),
        AHEAD("Ahead"),
        LEFT_AHEAD("LeftAhead"),
        RIGHT_AHEAD("RightAhead");

        //the token which identifies a direction - used for parsing
        private final String token;

        /**
         * Creates a new sense direction from the specified string token. Case insensitive.
         *
         * @param token the token which identifies the direction in a user's ant-brain file
         */
        Direction(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        /**
         * Finds the direction associated with a string token. Case insensitive.
         *
         * @param token the token for which to find the associated direction
         * @param insn the instruction identifier (line number)
         * @return the direction associated with the passed token
         * @throws ParseException if the token is not associated with any valid direction
         */
        public static Direction from(String token, int insn) throws ParseException {
            for (Direction direction : values()) {
                if (direction.token.equalsIgnoreCase(token)) {
                    return direction;
                }
            }
            throw new ParseException(
                    String.format(
                            "expected [%s], got %s",
                            Arrays.asList(values()).stream().map(Direction::toString).collect(Collectors.joining("|")),
                            token
                    ),
                    insn
            );

        }
    }

}
