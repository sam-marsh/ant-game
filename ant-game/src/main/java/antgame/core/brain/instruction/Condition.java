package antgame.core.brain.instruction;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public enum Condition {

    FRIEND("Friend"),
    FOE("Foe"),
    FRIEND_WITH_FOOD("FriendWithFood"),
    FOE_WITH_FOOD("FoeWithFood"),
    FOOD("Food"),
    ROCK("Rock"),
    MARKER("Marker"), //TODO how to implement 'Marker i'?
    FOE_MARKER("FoeMarker"),
    HOME("Home"),
    FOE_HOME("FoeHome");

    private final String token;

    Condition(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

    public static Condition from(String token, int line) throws ParseException {
        for (Condition condition : values()) {
            if (condition.token.equals(token)) {
                return condition;
            }
        }
        throw new ParseException(
                String.format(
                        "expected [%s], got %s",
                        Arrays.asList(values()).stream().map(Condition::toString).collect(Collectors.joining("|")),
                        token
                ),
                line
        );

    }
}
