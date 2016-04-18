package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public class SenseInstruction extends Instruction {

    public SenseInstruction() {

    }

    public enum Type {

        HERE("Here"),
        AHEAD("Ahead"),
        LEFT_AHEAD("LeftAhead"),
        RIGHT_AHEAD("RightAhead");

        private final String token;

        Type(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        public static Type from(String token, int line) throws ParseException {
            for (Type type : values()) {
                if (type.token.equals(token)) {
                    return type;
                }
            }
            throw new ParseException(
                    String.format(
                            "expected [%s], got %s",
                            Arrays.asList(values()).stream().map(Type::toString).collect(Collectors.joining("|")),
                            token
                    ),
                    line
            );

        }
    }

}
