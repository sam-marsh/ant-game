package antgame;

import java.io.IOException;

/**
 * This is a class description.
 *
 * @author the author
 */
public class Example {

    /**
     * This is an example of embedding code, like {@code runTest()}.
     * You can also link to other stuff in the code {@link #exampleMethod()} or {@link Example#exampleMethod()}
     * You can use <b>html</b> stuff if you <i>want</i>
     *
     * @param args
     * @see Example
     */
	public static void main(String[] args) {
		System.out.print("Test");
	}

    /**
     *
     * @return something
     */
	public int exampleMethod() { return 1; }

    /**
     * Note that Javadoc comments start with two stars.
     *
     * @param a fdgsdsfkndfjsn
     * @throws IOException if this
     * @throws IndexOutOfBoundsException if that
     */
    public void dangerousMethod(Object a) throws IOException, IndexOutOfBoundsException {
        if (a == null) throw new RuntimeException();
    }
}
