package antgame.core.brain.instruction;

import java.text.ParseException;

/**
 * Represents an abstract instruction, i.e. an 'order' for an ant to do something. These instructions
 * are specified by ant-brain files, and the instances of this class (or more specifically, the subclasses)
 * are produced from parsing this file using {@link antgame.core.brain.parser.BrainParser}. Each instruction
 * is identified by an integer, which is the line number in the file. Generally each instruction also includes
 * a reference to either one or two other instructions, which the ant (holding a finite state machine) will
 * transition to after the instruction is executed.
 *
 * @author Sam Marsh
 */
public abstract class Instruction {

    //the instruction number
    private final int insn;

    //the instruction type
    private final Type type;

    //the state to move to on failure
    protected Instruction success;

    //the state to move to on success
    protected Instruction failure;

    /**
     * Creates a new instruction with the given instruction number.
     *
     * @param insn the instruction identifier (line number)
     * @param type the type of instruction
     */
    public Instruction(int insn, Type type) {
        this.insn = insn;
        this.type = type;
    }

    /**
     * @return the instruction identified for this instruction
     */
    public int getID() {
        return insn;
    }

    /**
     * @return the instruction type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the next instruction for a particular ant to transition to if the instruction was successful
     */
    public final Instruction success() {
        return success;
    }

    /**
     * @return the next instruction for a particular ant to transition to if the instruction failed
     */
    public final Instruction failure() {
        return failure;
    }

    /**
     * Sets the state to transition to on successful execution of this instruction.
     *
     * @param success the state to transition to
     */
    public void success(Instruction success) {
        this.success = success;
    }

    /**
     * Sets the state to transition to on failed execution of this instruction.
     *
     * @param failure the state to transition to
     */
    public void failure(Instruction failure) {
        this.failure = failure;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Instruction && ((Instruction) o).insn == insn;
    }

    /**
     * Represents an instruction type.
     */
    public enum Type {

        SENSE("Sense"),
        MARK("Mark"),
        UNMARK("Unmark"),
        PICK_UP("PickUp"),
        DROP("Drop"),
        TURN("Turn"),
        MOVE("Move"),
        FLIP("Flip");

        //the token used to describe the type - used in the parser
        private final String token;

        /**
         * Creates a new instruction type. Case insensitive.
         *
         * @param token the token used to describe the type, which can be specified in the ant-brain file by
         *              users
         */
        Type(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }

        /**
         * Finds the instruction type associated with a particular token. Case insensitive.
         *
         * @param token the token to interpret as an instruction type
         * @param insn the line number of the token (the instruction identifier) - used in the exception
         *             if a {@link ParseException} is thrown
         * @return the type associated with the token
         * @throws ParseException if the token is not associated with any instruction type
         */
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
