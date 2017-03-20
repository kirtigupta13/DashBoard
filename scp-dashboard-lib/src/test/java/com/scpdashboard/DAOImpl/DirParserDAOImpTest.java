package com.scpdashboard.DAOImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.scpdashboard.DAOImpl.DirParserDAOImpl;
import com.scpdashboard.models.DirServer;

/**
 * Test the {@link DirParserDAOImpl} class.
 * 
 * @author Kirti Gupta KG048707.
 */
@RunWith(MockitoJUnitRunner.class)
public class DirParserDAOImpTest {

    private final static String FIRST_LINE = "id  description";
    private final static String SECOND_LINE = "------------------";
    private final DirParserDAOImpl dirParserDAOImpl = new DirParserDAOImpl();
    private final BufferedReader mockBufferedReader = Mockito.mock(BufferedReader.class);

    /**
     * Tests that the {@link DirParserDAOImpl#parseFile(BufferedReader)} method returns {@link DirServer} as a list.
     * 
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws NumberFormatException
     *             Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that
     *             the string does not have the appropriate format.
     */
    @Test
    public void testParseFile_createDirServersList() throws IOException, NumberFormatException {
        final String thirdLine = "0    Service Manager";
        final List<DirServer> expectedDirServers = Arrays.asList(new DirServer(0, "Service Manager"));
        when(mockBufferedReader.readLine()).thenReturn(FIRST_LINE, SECOND_LINE, thirdLine, null);

        final List<DirServer> actualDirServers = dirParserDAOImpl.parseFile(mockBufferedReader);

        assertEquals(expectedDirServers, actualDirServers);
    }

    /**
     * Tests that the {@link DirParserDAOImpl#parseFile(BufferedReader)} creates an empty {@link DirServer} list when server
     * information have not been received by executing dir command.
     * 
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws NumberFormatException
     *             Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that
     *             the string does not have the appropriate format.
     */
    @Test
    public void testParseFile_DirServersListIsEmpty() throws IOException, NumberFormatException {
        when(mockBufferedReader.readLine()).thenReturn(FIRST_LINE, SECOND_LINE, null);

        final List<DirServer> actualDirServers = dirParserDAOImpl.parseFile(mockBufferedReader);

        assertThat("DirServer List is empty.", actualDirServers, hasSize(0));
    }

    /**
     * Test that the {@link DirParserDAOImpl#parseFile(BufferedReader)} throws a {@link IllegalArgumentException} when BufferReader
     * is null.
     * 
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws NumberFormatException
     *             Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that
     *             the string does not have the appropriate format.
     * @throws IllegalArgumentException
     *             when BufferedReader is null.
     */
    @Test
    public void testParseFile_BufferReaderIsNull() throws IOException, NumberFormatException {
        try {
            dirParserDAOImpl.parseFile(null);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals("BufferedReaderObject Object is Null.", e.getMessage());
        }
    }

    /**
     * Test that the {@link DirParserDAOImpl#parseFile(BufferedReader)} throws an {@link NumberFormatException} when server
     * information received by executing dir command is not valid.
     * 
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws NumberFormatException
     *             Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that
     *             the string does not have the appropriate format.
     */
    @Test
    public void testParseFile_InvalidDirServerInformation() throws IOException, NumberFormatException {
        try {
            final String thirdLine = "InvalidDirServerInformation";
            when(mockBufferedReader.readLine()).thenReturn(FIRST_LINE, SECOND_LINE, thirdLine).thenReturn(null);

            dirParserDAOImpl.parseFile(mockBufferedReader);
            fail("NumberFormatException has not  been thrown.");
        } catch (final NumberFormatException e) {
            assertEquals("For input string: \"InvalidDirServerInformation\"", e.getMessage());
        }
    }
}
