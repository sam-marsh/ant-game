package antgame.core;

/**
 * @author Sam Marsh
 */
public class MatchOutcome {

    private PlayerOutcome red;
    private PlayerOutcome black;

    public MatchOutcome(PlayerOutcome red, PlayerOutcome black) {
        this.red = red;
        this.black = black;
    }

    public PlayerOutcome getRedPlayerOutcome() {
        return red;
    }

    public PlayerOutcome getBlackPlayerOutcome() {
        return black;
    }

    public boolean wasDraw() {
        return red.getResult() == PlayerOutcome.Result.DRAW;
    }

}
