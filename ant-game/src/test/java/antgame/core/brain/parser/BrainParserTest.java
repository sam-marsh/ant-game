package antgame.core.brain.parser;

import antgame.core.Colony;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * @author Sam Marsh
 */
public class BrainParserTest {

    @Test
    public void test() throws IOException, ParseException {
        BrainParser.parse(Colony.Colour.RED, new File("/Users/Sam/Projects/ant-game/samples/brain/ant-brain-1.txt"));
    }
}