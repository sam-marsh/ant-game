package antgame.core.brain;

/**
 * @author Sam Marsh
 */
public enum InstructionType {

    SENSE("Sense"),
    MARK("Mark"),
    UNMARK("Unmark"),
    PICK_UP("PickUp"),
    DROP("Drop"),
    TURN("Turn"),
    MOVE("Move"),
    FLIP("Flip");

    private final String token;

    InstructionType(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

}
