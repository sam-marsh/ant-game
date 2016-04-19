package antgame.core.brain;

import antgame.core.brain.instruction.Instruction;

/**
 * @author Sam Marsh
 */
public class Brain {

    private Instruction instruction;

    public Brain(Instruction initial) {
        this.instruction = initial;
    }


}
