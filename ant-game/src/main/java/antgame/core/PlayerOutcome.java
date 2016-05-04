package antgame.core;

/**
 * Represents an outcome for a player from playing in a match.
 *
 * @author Sam Marsh
 */
public class PlayerOutcome {

    //the relevant player
    private final Player player;

    //what was the result?
    private final Result result;

    //which match
    private final Match match;

    /**
     * Creates a new outcome of a match or tournament.
     *
     * @param player the player in question
     * @param result the result for this player
     * @param match the match to which this outcome applies
     */
    public PlayerOutcome(Player player, Result result, Match match) {
        this.player = player;
        this.result = result;
        this.match = match;
    }

    /**
     * @return the player which this result applies to
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the result for this player
     */
    public Result getResult() {
        return result;
    }

    /**
     * @return the match to which this outcome applies
     */
    public Match getMatch() {
        return match;
    }

    /**
     * A possible outcome for a match or tournament.
     */
    public enum Result {

        WIN(2),
        LOSS(0),
        DRAW(1);

        //the number of points gained for this result
        private final int points;

        /**
         * Creates a new result type from a match/tournament.
         *
         * @param points the number gained for this result
         */
        Result(int points) {
            this.points = points;
        }

        /**
         * @return the number of points gained for this outcome
         */
        public int getPoints() {
            return points;
        }

    }

}
