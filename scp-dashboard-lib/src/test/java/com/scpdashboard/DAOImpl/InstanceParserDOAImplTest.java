package com.scpdashboard.DAOImpl;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;

import com.scpdashboard.dao.ParserDAO;
import com.scpdashboard.exceptions.NotValidServerFileException;
import com.scpdashboard.models.ServerInstanceInfo;

@RunWith(MockitoJUnitRunner.class)
public class InstanceParserDOAImplTest {
    @Mock
    private BufferedReader mockBufferedReader;

    private ParserDAO<ServerInstanceInfo> instanceParser = null;

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        instanceParser = new InstanceParserDAOImpl();
    }

    /**
     * Test which checks the size of the Instances list and the creation of Instance Object
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */

    @Test
    public void testInstanceParseFileWithNecessaryDatatoCreateInstance()
            throws IOException, NumberFormatException, NotValidServerFileException {

        String line1 = "id     description                               msgs     state";
        String line2 = "--------------------------------------------------------------------";
        String line3 = "147    Custom 200 server -old version            0        running";
        ServerInstanceInfo exceptedServerInstacneInfo = new ServerInstanceInfo(147, "Custom 200 server -old version", 0, "running");

        when(mockBufferedReader.readLine()).thenReturn(line1, line2, line3, null);

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(mockBufferedReader);

        assertThat("ServerInstanceinfo of size 1 should be created", instances, hasSize(1));
        assertEquals("The instance object has not been created with the ecpected values", instances.get(0).toString(),
                exceptedServerInstacneInfo.toString());

    }

    /**
     * Test which checks that the Instance object is not created when the file does not have any information about the Server
     * instances
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */
    @Test
    public void testInstanceParseFileWithUnNecessaryDotsData()
            throws IOException, NumberFormatException, NotValidServerFileException {
        String line1 = "id     description                               msgs     state";
        String line2 = "--------------------------------------------------------------------";

        when(mockBufferedReader.readLine()).thenReturn(line1, line2, null);

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(mockBufferedReader);

        assertThat("There is no Instance data to create an instance. But the size of is not zero", instances, hasSize(0));

    }

    /**
     * Testing whether the parser does not accept any junk information files apart from the file which has server Instance
     * information.
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */
    @Test
    public void testInstanceParseFileDoesNotContainInstanceInfo()
            throws IOException, NumberFormatException, NotValidServerFileException {
        String line = "I am a junk file";
        String line1 = "I don't have necessary information that has to do with Server Instance Inforamtion";
        String line2 = "I am not related to what you are looking for";

        when(mockBufferedReader.readLine()).thenReturn(line, line1, line2).thenReturn(null);
        expectedEx.expect(NotValidServerFileException.class);
        expectedEx.expectMessage("The given file does not contain proper valid server Information");

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(mockBufferedReader);

    }

    /**
     * Testing the Instance Parser to check whether it ignores the empty lines after the Parsing has
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */

    @Test
    public void testInstanceParseFileWithEmptyStringInMiddle()
            throws IOException, NumberFormatException, NotValidServerFileException {
        String line1 = "id     description                               msgs     state";
        String line2 = "--------------------------------------------------------------------";
        String line3 = "";
        String line4 = "147    Custom 200 server -old version            0        running";
        ServerInstanceInfo exceptedServerInstacneInfo = new ServerInstanceInfo(147, "Custom 200 server -old version", 0, "running");

        when(mockBufferedReader.readLine()).thenReturn(line1, line2, line3, line4).thenReturn(null);

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(mockBufferedReader);

        assertThat("ServerInstanceinfo of size 1 has not been created", instances, hasSize(1));
        assertEquals("The instance object has not been created with the ecpected values", instances.get(0).toString(),
                exceptedServerInstacneInfo.toString());

    }

    /**
     * Testing the Instance Parser to check whether it throws a Null Pointer Exception when the Buffered Reader Object is Null
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */

    @Test
    public void testInstanceParseFileBufferReaderValueNull()
            throws IOException, NumberFormatException, NotValidServerFileException {
        String line = "id     description                               msgs     state";

        when(mockBufferedReader.readLine()).thenReturn(line).thenReturn(null);
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("BufferedReaderObject Object is Null");

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(null);

    }

    /**
     * Testing to check whether the parser throws exception if it finds any junk information in place of actual server instance
     * information.
     * 
     * @throws IOException
     * @throws NumberFormatException
     * @throws NotValidServerFileException
     */

    @Test
    public void testInstanceParseFileWithJunkValuesInplaceOfActualInstanceInformation()
            throws IOException, NumberFormatException, NotValidServerFileException {
        String line = "id     description                               msgs     state";
        String line1 = "--------------------------------------------------------------------";
        String line2 = "I am Junk, this is not Server Instance information";

        when(mockBufferedReader.readLine()).thenReturn(line, line1, line2).thenReturn(null);
        expectedEx.expect(NumberFormatException.class);

        final List<ServerInstanceInfo> instances = instanceParser.parseFile(mockBufferedReader);

        assertThat("No Server Instance Information should be created. There is some junk information", instances, hasSize(0));

    }

}
