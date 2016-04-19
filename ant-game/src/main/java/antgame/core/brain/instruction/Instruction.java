package antgame.core.brain.instruction;

import java.text.ParseException;

/**
 * @author Sam Marsh
 */
public abstract class Instruction {

    private int insn;

    public Instruction(int insn) {
        this.insn = insn;
    }

    public int getInstruction() {
        return insn;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Instruction && ((Instruction) o).insn == insn;
    }

    public enum Type {

        SENSE("Sense"),
        MARK("Mark"),
        UNMARK("Unmark"),
        PICK_UP("PickUp"),
        DROP("Drop"),
        TURN("Turn"),
        MOVE("Move"),
        FLIP("Flip");

        private final String token;

        Type(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        public static Type parse(String token, int insn) throws ParseException {
            for (Type type : values()) {
                if (type.token.equalsIgnoreCase(token)) {
                    return type;
                }
            }
            throw new ParseException("no such instruction type: " + token, insn);
        }

    }

}
