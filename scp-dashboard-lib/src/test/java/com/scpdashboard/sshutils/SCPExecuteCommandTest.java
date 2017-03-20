package com.scpdashboard.sshutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;

/**
 * Test the {@link SCPExecuteCommand} class.
 * 
 * @author Kirti Gupta KG048707.
 */
public class SCPExecuteCommandTest {
    private final static String DOMAINNAME = "surround";
    private final static String COMMAND = "server -entry 101";
    private final static String HOSTNAME = "fork";
    private final static String SCPUSERNAME = "sd049814";
    private final static String SCPPASSWORD = "GO";
    private final static String COMMAND_INVALID_ERROR_MSG = "Command cannot be null/empty/blank.";
    public final static String SCPUSERSESSION_NOTNULL_EXCEPTION_MESSAGE = "Error establishing connection.Scp User Session cannot be null.";
    private final static String SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE = "Error establishing connection.SessionChannel cannot be null.";
    private final static String output = "echo \'" + SCPUSERNAME + "\n" + DOMAINNAME + "\n" + SCPPASSWORD + "\n " + COMMAND
            + "\' | scpview " + HOSTNAME + "\n";

    private final SCPExecuteCommand scpExecuteCommand = new SCPExecuteCommand();
    private final SessionChannelClient mockSessionChannelClient = mock(SessionChannelClient.class);
    private final SCPUserSession scpUserSession = new SCPUserSession(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME,
            mockSessionChannelClient);
    private final SessionOutputReader mocksessionOutputReader = mock(SessionOutputReader.class);
    private final ChannelOutputStream mockChannelOutputStream = mock(ChannelOutputStream.class);
    private final SessionOutputReader mockSessionOutputReader = mock(SessionOutputReader.class);
    private final SessionChannelClient channelClient = null;

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method returns String Blob.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        when(mockSessionChannelClient.getOutputStream()).thenReturn(mockChannelOutputStream);
        when(mocksessionOutputReader.getOutput()).thenReturn(output);

        final String actual = scpExecuteCommand.execSCPCommand(COMMAND, scpUserSession, mocksessionOutputReader);

        verify(mockSessionChannelClient, times(1)).startShell();
        verify(mockChannelOutputStream, times(1)).write(output.getBytes());
        assertEquals(output, actual);
    }

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method throws an
     * {@link IllegalArgumentException} when a empty command is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand_CommandEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            scpExecuteCommand.execSCPCommand("", scpUserSession, mockSessionOutputReader);

            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            verify(mockSessionChannelClient, times(0)).startShell();
            verify(mockChannelOutputStream, times(0)).write(output.getBytes());
            assertEquals(COMMAND_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method throws an
     * {@link IllegalArgumentException} when a blank command is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand_CommandBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            scpExecuteCommand.execSCPCommand(" ", scpUserSession, mockSessionOutputReader);

            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            verify(mockSessionChannelClient, times(0)).startShell();
            verify(mockChannelOutputStream, times(0)).write(output.getBytes());
            assertEquals(COMMAND_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method throws an
     * {@link IllegalArgumentException} when a blank command is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand_CommandNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            scpExecuteCommand.execSCPCommand(null, scpUserSession, mockSessionOutputReader);

            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            verify(mockSessionChannelClient, times(0)).startShell();
            verify(mockChannelOutputStream, times(0)).write(output.getBytes());
            assertEquals(COMMAND_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method throws an
     * {@link UserAuthenticationInvalidExeception} when ScpUserSession is null.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand_ScpUserSessionNull()
            throws IOException, InterruptedException, UserAuthenticationInvalidExeception {
        try {
            scpExecuteCommand.execSCPCommand(COMMAND, null);

            fail("UserAuthenticationInvalidExeception should have been thrown.");
        } catch (final UserAuthenticationInvalidExeception e) {
            verify(mockSessionChannelClient, times(0)).startShell();
            verify(mockChannelOutputStream, times(0)).write(output.getBytes());
            assertEquals(SCPUSERSESSION_NOTNULL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SCPExecuteCommand#execSCPCommand(String, {@link SCPUserSession})} method throws an
     * {@link UserAuthenticationInvalidExeception} when SessionChannelClient is null.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Session Channel Client is null.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testSCPExecuteCommand_SessionChannelClientNull()
            throws IOException, InterruptedException, UserAuthenticationInvalidExeception {
        try {
            final SCPUserSession scpUserSession = new SCPUserSession(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, channelClient);
            scpExecuteCommand.execSCPCommand(COMMAND, scpUserSession);

            fail("UserAuthenticationInvalidExeception should have been thrown.");
        } catch (final UserAuthenticationInvalidExeception e) {
            verify(mockChannelOutputStream, times(0)).write(output.getBytes());
            assertEquals(SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
