package antgame.core.brain.state;

/**
 * @author Sam Marsh
 */
public abstract class BrainState {

    private int state;

    public BrainState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public abstract BrainState next();

}
