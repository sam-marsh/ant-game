package antgame.core;

/**
 * Each match has two outcomes, one associated with each player. This class holds a pair
 * of these outcomes.
 *
 * @author Sam Marsh
 */
public class MatchOutcome {

    //the outcome for the red player
    private PlayerOutcome red;

    //the outcome for the black player
    private PlayerOutcome black;

    /**
     * Creates a match outcome composed of a red-player outcome and a black-player outcome.
     *
     * @param red the red player result
     * @param black the black player result
     */
    public MatchOutcome(PlayerOutcome red, PlayerOutcome black) {
        this.red = red;
        this.black = black;
    }

    /**
     * @return the result of the red player
     */
    public PlayerOutcome getRedPlayerOutcome() {
        return red;
    }

    /**
     * @return the result of the black player
     */
    public PlayerOutcome getBlackPlayerOutcome() {
        return black;
    }

    /**
     * @return {@code true} if a draw, otherwise {@code false}
     */
    public boolean wasDraw() {
        return red.getResult() == PlayerOutcome.Result.DRAW;
    }

}
