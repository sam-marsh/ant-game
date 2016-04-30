package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents an instruction for an ant to turn in a given direction.
 *
 * @author Sam Marsh
 */
public class TurnInstruction extends Instruction {

    //the direction in which to turn
    private final Direction direction;

    /**
     * Creates a new turn instruction.
     *
     * @param insn the instruction identifier (line number)
     * @param direction the direction in which to turn
     */
    public TurnInstruction(int insn, Direction direction) {
        super(insn, Type.TURN);
        this.direction = direction;
    }

    /**
     * @return the direction in which to turn
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Represents a possible turn direction.
     */
    public enum Direction {

        LEFT("Left"),
        RIGHT("Right");

        //the token identifying this direction - case insensitive
        private String token;

        /**
         * Creates a new possible turning direction, specified by the given token
         * which is used for parsing. Case insensitive.
         *
         * @param token the token which uniquely identifies the direction
         */
        Direction(String token) {
            this.token = token;
        }

        /**
         * Finds the turn direction associated with a given token (generally from a user's ant brain
         * file). Case insensitive.
         *
         * @param token the token for which to find the associated direction
         * @param insn the instruction identifier (line number)
         * @return the direction associated with the passed token
         * @throws ParseException if no such direction is associated with the token
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
