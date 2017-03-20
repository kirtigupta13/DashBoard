package com.scpdashboardrestintegration.apitest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboard.sshutils.SSHClientConnect;
import com.scpdashboardREST.api.ServerInstancesRetrieverHandler;
import com.scpdashboardREST.exceptions.ResponseErrorException;

import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class ServerInstancesRetriverTest {
    private ServerInstancesRetrieverHandler serverInstancesHandlerObj = null;
    private final String serverIdWithInstancesInfo = "101";
    private final String randomStringAsServerId = "fkanskds";
    private final String correctSCPUserName = "SD049814";
    private final String correctScpPassword = "go";
    private final String correcthostname = "fork";
    private final String correctdomain = "Surround";
    private final String correctsshUsername = "kg048707";
    private final String correctsshPassword = "go";
    private final String incorrectValue = "incorrect";
    private final String serverIdWithNoInstances = "203";

    @Mock
    private Request mockRequest;

    @Mock
    private Response mockResponse;

    @Before
    public void setup() {
        serverInstancesHandlerObj = new ServerInstancesRetrieverHandler();
    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check that the test does not throw any exception and returns list of
     * instances information when proper credentials and server id is given.
     */
    @Test
    public void testWhenProperCommandAndFrontEndCredentialsAreGiven() {
        List<ServerInstanceInfo> actual_instances = Collections.emptyList();

        when(mockRequest.params("serverId")).thenReturn(serverIdWithInstancesInfo);

        SCPUserSession session = createScpUserSession(correctSCPUserName, correctScpPassword, correcthostname, correctdomain,
                correctsshUsername, correctsshPassword);
        try {
            actual_instances = serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, session);

        } catch (ResponseErrorException e) {
            fail("The test should not throw any ResponseErrorException with message" + e.getMessage()
                    + " since the details given are correctly.");
        }
    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check whether it throws ResponseErrorException with valid status and
     * message when serverId is not given properly.
     */
    @Test
    public void testWhenNoProperServerIdIsGiven() {
        when(mockRequest.params("serverId")).thenReturn(randomStringAsServerId);
        SCPUserSession session = createScpUserSession(correctSCPUserName, correctScpPassword, correcthostname, correctdomain,
                correctsshUsername, correctsshPassword);
        try {
            serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, session);
            fail("Test supposed to fail because proper ServerId is not given, but it did not!");
        } catch (ResponseErrorException e) {
            assertEquals("The status is not 422", e.getStatus(), 422);
            assertEquals(e.getMessage(), "The given file does not contain proper valid server Information");

        }

    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check whether it throws ResponseErrorException with valid status and
     * message when wrong front-end username(ScpUserName) is given.
     */
    @Test
    public void testWhenWrongSCPUsernameIsGiven() {
        when(mockRequest.params("serverId")).thenReturn(serverIdWithInstancesInfo);
        SCPUserSession session = createScpUserSession(incorrectValue, correctScpPassword, correcthostname, correctdomain,
                correctsshUsername, correctsshPassword);
        try {
            serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, session);
            fail("Test supposed to fail because wrong SCPUserName is given, but it did not!");
        } catch (ResponseErrorException e) {
            assertEquals("The status is not 422", e.getStatus(), 422);
            assertEquals(e.getMessage(), "The given file does not contain proper valid server Information");

        }
    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check whether it throws ResponseErrorException with valid status and
     * message when wrong front-end password(ScpPassword) is given.
     */
    @Test
    public void testWhenWrongSCPPasswordIsGiven() {
        when(mockRequest.params("serverId")).thenReturn(serverIdWithInstancesInfo);
        SCPUserSession session = createScpUserSession(incorrectValue, correctScpPassword, correcthostname, correctdomain,
                correctsshUsername, correctsshPassword);
        try {
            serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, session);
            fail("Test supposed to fail because wrong SCP Password is given, but it did not!");
        } catch (ResponseErrorException e) {
            assertEquals("The status is not 422", e.getStatus(), 422);
            assertEquals(e.getMessage(), "The given file does not contain proper valid server Information");

        }
    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check whether it throws ResponseErrorException with valid status and
     * message when server id which has no instances is given.
     */
    @Test
    public void testWhenServerIdWithNoServerInstancesIsGiven() {
        when(mockRequest.params("serverId")).thenReturn(serverIdWithNoInstances);
        SCPUserSession session = createScpUserSession(correctSCPUserName, correctScpPassword, correcthostname, correctdomain,
                correctsshUsername, correctsshPassword);
        try {
            serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, session);
            fail("Test supposed to fail because serverId with no instances is given, but it did not!");
        } catch (ResponseErrorException e) {
            assertEquals("The status is not 422", e.getStatus(), 422);
            assertEquals(e.getMessage(), "There are no instances Present with the serverid " + serverIdWithNoInstances);

        }
    }

    /**
     * Test {@link ServerInstancesRetrieverHandler.java} to check whether it throws ResponseErrorException with valid status and
     * message when session object is null.
     */
    @Test
    public void testWhenSessionObjectisNullWhenCallingExecuteCommand() {
        when(mockRequest.params("serverId")).thenReturn(serverIdWithNoInstances);
        try {
            serverInstancesHandlerObj.serverInstancesHandler(mockRequest, mockResponse, null);
            fail("Test supposed to fail because session object is null, but it did not!");
        } catch (ResponseErrorException e) {
            assertEquals("The status is not 422", e.getStatus(), 422);
            assertEquals(e.getMessage(), "Error establishing connection.Scp User Session cannot be null.");

        }

    }

    /**
     * A private function which is responsible for creating a mock session wrapper object that will be used for the tests. This
     * returns null when exception is thrown or else returns us a session object
     * 
     * @param SCPUserName
     * @param ScpPassword
     * @param hostname
     * @param domain
     * @param sshUsername
     * @param sshPassword
     * @return ScpUserSession
     */
    private SCPUserSession createScpUserSession(String SCPUserName, String ScpPassword, String hostname, String domain,
            String sshUsername, String sshPassword) {
        SCPUserSession session = null;

        try {
            session = new SSHClientConnect().connect(SCPUserName, ScpPassword, hostname, domain, sshUsername, sshPassword);
        } catch (IOException | UserAuthenticationInvalidExeception e) {
            return null;
        }

        return session;

    }

}
