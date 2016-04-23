package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public class Condition {

    private final Type type;
    private final int marker;

    public Condition(Type type) {
        this.type = type;
        this.marker = -1;
    }

    public Condition(int marker) {
        this.type = Type.MARKER;
        this.marker = marker;
    }

    public Type getType() {
        return type;
    }

    public int getMarker() {
        if (type != Type.MARKER) {
            throw new AssertionError("internal error: calling getMarker() on non-marker condition");
        }
        return marker;
    }

    public enum Type {

        FRIEND("Friend"),
        FOE("Foe"),
        FRIEND_WITH_FOOD("FriendWithFood"),
        FOE_WITH_FOOD("FoeWithFood"),
        FOOD("Food"),
        ROCK("Rock"),
        MARKER("Marker"),
        FOE_MARKER("FoeMarker"),
        HOME("Home"),
        FOE_HOME("FoeHome");

        private final String token;

        Type(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        public static Type from(String token, int line) throws ParseException {
            for (Type condition : values()) {
                if (condition.token.equals(token)) {
                    return condition;
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
