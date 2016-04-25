package antgame.core.brain.instruction;

import antgame.core.world.Marker;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a type of sense condition. Ants can perform checks on cells, passing an instance of
 * {@link Condition} as an argument. They then go to one state if the condition is {@code true} for
 * that cell, and another state if {@code false}. So the {@link antgame.core.world.World} can receive
 * conditions and check them for particular cells.
 *
 * @author Sam Marsh
 */
public class Condition {

    //the type of condition - e.g. FRIEND or MARKER
    private final Type type;

    //only relevant for a MARKER-type condition - holds the marker integer, which can range from 0..5
    private final Marker marker;

    /**
     * Creates a new condition of the given type. This constructor is used for creating all conditions
     * except for those of type {@link Type#MARKER}, for {@link Condition#Condition(Marker)} must be used
     * instead.
     *
     * @param type the type, e.g. {@link Type#FOE}
     */
    public Condition(Type type) {
        this.type = type;
        this.marker = null;
        if (type == Type.MARKER) {
            throw new AssertionError("internal error: creating marker type using wrong constructor");
        }
    }

    /**
     * Creates a new {@link Type#MARKER} condition, which represents a check that a particular cell
     * contains a marker (left from an ant from the same colony) with the given index (which ranges
     * from 0..5).
     *
     * @param marker the marker index, ranging from 0..5
     */
    public Condition(Marker marker) {
        this.type = Type.MARKER;
        this.marker = marker;
    }

    /**
     * @return the type of marker
     */
    public Type getType() {
        return type;
    }

    /**
     * Only used for {@link Type#MARKER} conditions - returns the 'type' of marker.
     *
     * @return the marker index
     */
    public Marker getMarker() {
        if (type != Type.MARKER) {
            throw new AssertionError("internal error: calling getMarker() on non-marker condition");
        }
        return marker;
    }

    /**
     * Describes a type of sense condition.
     */
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

        //used in the parser - what keyword is used to describe this type?
        private final String token;

        /**
         * Creates a new condition type.
         *
         * @param token the token used for parsing (case-insensitive)
         */
        Type(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        /**
         * Finds the {@link Type} associated with the passed token. Used for parsing
         * ant-brain files. Case insensitive.
         *
         * @param token the token to find the associated {@link Type} for
         * @param insn the instruction number - used in the thrown exception if no associated
         *             type exists for the passed token
         * @return the {@link Type} associated with the token
         * @throws ParseException
         */
        public static Type from(String token, int insn) throws ParseException {
            for (Type condition : values()) {
                if (condition.token.equalsIgnoreCase(token)) {
                    return condition;
                }
            }
            throw new ParseException(
                    String.format(
                            "expected [%s], got %s",
                            Arrays.asList(values()).stream().map(Type::toString).collect(Collectors.joining("|")),
                            token
                    ),
                    insn
            );

        }
    }
}
