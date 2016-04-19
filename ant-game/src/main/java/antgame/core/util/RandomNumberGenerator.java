package antgame.core.util;

/**
 * A utility class for generating random numbers.
 *
 * @author Sam Marsh
 */
public class RandomNumberGenerator {

    //the multiplicative constant for the s_n sequence.
    private static final int SEQ_MUL = 22695477;

    //the division constant for the x_n sequence
    private static final int SEQ_DIV = 65536;

    //the modulo constant for the x_n sequence
    private static final int SEQ_MOD = 16384;

    //the current value of the s_n sequence
    private int sequence;

    /**
     * Creates a new pseudo-random number generator, with a seed based on the current
     * time in milliseconds.
     */
    public RandomNumberGenerator() {
        this((int) System.currentTimeMillis());
    }

    /**
     * Creates a new pseudo-random number generator, with the given seed.
     *
     * @param seed the seed for the RNG
     */
    public RandomNumberGenerator(int seed) {
        this.sequence = seed; // s_0

        //x_0 value depends on s_4, so compute up to s_3 first
        nextSequence(); //s_1
        nextSequence(); //s_2
        nextSequence(); //s_3
    }

    /**
     * Generates a new random number in the range [0, n-1] (inclusive).
     *
     * @param n the (excluded) upper bound for the produced number (that is, the returned value will be between 0 and
     *          one less than this upper bound
     * @return a pseudo-random number
     */
    public int next(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("require n > 0, was " + n);
        }
        //step the sequence forward one
        nextSequence();
        //for some reason (probably overflow) only get correct numbers when treating as unsigned, so all arithmetic
        // following is done by treating the values as unsigned integers
        int val = Integer.divideUnsigned(sequence, SEQ_DIV);
        return Integer.remainderUnsigned(Integer.remainderUnsigned(val, SEQ_MOD), n);
    }

    /**
     * Computes the next integer in the sequence - i.e. calculates and stores s_(n+1) from s_n.
     */
    private void nextSequence() {
        sequence *= SEQ_MUL;
        sequence += 1;
    }

}
