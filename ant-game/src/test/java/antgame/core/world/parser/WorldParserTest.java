package antgame.core.world.parser;

import antgame.core.AntTest;
import antgame.core.world.World;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Tests the  {@link WorldParser} class.
 *  Created by Arsalan on 03/05/2016.
 */
public class WorldParserTest {


    /**
     * Tests The World Parser on a correctly formed World File
     * @see WorldParser#parse(File)
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testValid() throws IOException, ParseException
    {
        World world = WorldParser.parse(
                new File(AntTest.class.getResource("/world/ant-world-1.txt").getFile()));

        // Test World was Correctly Parsed
        assertEquals(150,world.height());
        assertEquals(150,world.width());
    }


    /**
     * Tests The World Parser on an Invalid World file consisting of invalid tokens
     * @see WorldParser#parse(File)
     * @throws IOException
     * @throws ParseException
     */
    @Test (expected = ParseException.class)
    public void testInvalid() throws IOException, ParseException
    {
        World world = WorldParser.parse(
                new File(AntTest.class.getResource("/world/ant-world-invalid.txt").getFile()));
    }


    /**
     * Tests The World Parser on a World File that is too large
     * @see WorldParser#parse(File)
     * @throws IOException
     * @throws ParseException
     */
    @Test (expected = ParseException.class)
    public void testLarge() throws IOException, ParseException
    {
        World world = WorldParser.parse(
                new File(AntTest.class.getResource("/world/ant-world-large.txt").getFile()));
    }





}
