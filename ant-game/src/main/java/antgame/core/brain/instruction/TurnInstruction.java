package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public class TurnInstruction extends Instruction {

    private final Direction direction;
    private final Instruction st;

    public TurnInstruction(int insn, Direction direction, Instruction st) {
        super(insn);
        this.direction = direction;
        this.st = st;
    }

    public enum Direction {

        LEFT("Left"),
        RIGHT("Right");

        private String token;

        Direction(String token) {
            this.token = token;
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
