package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public class SenseInstruction extends Instruction {

    private final Direction direction;
    private final Instruction st1;
    private final Instruction st2;
    private final Condition condition;

    public SenseInstruction(int insn, Direction direction, Instruction st1, Instruction st2, Condition condition) {
        super(insn);
        this.direction = direction;
        this.st1 = st1;
        this.st2 = st2;
        this.condition = condition;
    }

    public Direction getDirection() {
        return direction;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public Instruction success() {
        return st1;
    }

    @Override
    public Instruction failure() {
        return st2;
    }

    public enum Direction {

        HERE("Here"),
        AHEAD("Ahead"),
        LEFT_AHEAD("LeftAhead"),
        RIGHT_AHEAD("RightAhead");

        private final String token;

        Direction(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

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
