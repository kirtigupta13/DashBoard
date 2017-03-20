package com.scpdashboard.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.scpdashboard.exception.UserAuthenticationInvalidExeception;
import com.sshtools.j2ssh.session.SessionChannelClient;

/**
 * Test the {@link SCPConnectController} class.
 * 
 * @author Kirti Gupta KG048707.
 */
@RunWith(MockitoJUnitRunner.class)
public class SSHClientConnectTestExeCommand {
    private final SSHClientConnect clientConnect = new SSHClientConnect();
    private SessionChannelClient connect = null;

    private static final String DOMAINNAME = "SURROUND";
    private static final String USERNAME = "SD049814";
    private static final String PASSWORD = "GO";
    private static final String COMMAND = "server -entry 101";
    private static final String HOSTNAME = "fork";

    private static final String USERNAME_INVALID = "UsernameNotValid";
    private static final String DOMAINNAME_INVALID = "DomainNameNotValid ";
    private static final String PASSWORD_INVALID = "PasswordNotValid";

    private static final String USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "User Name cannot be null/empty/blank.";
    private static final String DOMAIN_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "Domain name cannot be null/empty/blank.";
    private static final String PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "Password cannot be null/empty/blank.";
    private static final String COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "SCP command cannot be null/empty/blank.";
    private static final String SESSIONCHANNELCLIENT_NOTNULL_EXPECTED_MESSAGE = "sessionChannel cannot be null.";
    private static final String STRINGREADER_NOTNULL_EXPECTED_MESSAGE = "invalid execute command is passed";
    private static final String STRINGREADER_READY_EXPECTED_MESSAGE = "StringReader object stream is ready to be read.";

    @Before
    public void setUp() throws IOException, UserAuthenticationInvalidExeception {
        connect = clientConnect.connect("kg048707", "go", "fork");
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link NullPointerException} when a sessionChannelClient is null.
     * 
     * @throws IOException
     *             if an IO error occurs.
     * @throws NullPointerException
     *             Thrown when an application attempts to use null in a case where an object is required.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = NullPointerException.class)
    public void testExecuteWhenSessionChannelClientObjectIsNull() throws InterruptedException, IOException {
        try {
            clientConnect.execSCPCommand(null, COMMAND, USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
        } catch (final NullPointerException exception) {
            assertEquals(SESSIONCHANNELCLIENT_NOTNULL_EXPECTED_MESSAGE, exception.getMessage());
            throw exception;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a null user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenUserNameIsNull() throws InterruptedException, IOException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, null, DOMAINNAME, PASSWORD, HOSTNAME);
            fail(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a empty user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenUserNameIsEmpty() throws InterruptedException, IOException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, "", DOMAINNAME, PASSWORD, HOSTNAME);
            fail(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a null password is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenPasswordIsNull() throws InterruptedException, IOException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, USERNAME, DOMAINNAME, null, HOSTNAME);
            fail(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a empty password is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenpasswordIsEmpty()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, USERNAME, DOMAINNAME, "", HOSTNAME);
            fail(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a null domain name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenDomainNameIsNull()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, USERNAME, null, PASSWORD, HOSTNAME);
            fail(DOMAIN_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(DOMAIN_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a empty domain name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenDomainNameIsEmpty()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            clientConnect.execSCPCommand(connect, COMMAND, USERNAME, "", PASSWORD, HOSTNAME);
            fail(DOMAIN_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(DOMAIN_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a null command is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenCommandIsNull()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            clientConnect.execSCPCommand(connect, null, USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
            fail(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a empty command is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test(expected = IllegalStateException.class)
    public void testexecSCPCommandWhenCommandIsEmpty()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            clientConnect.execSCPCommand(connect, "", USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
            fail(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} returns StringReader
     * object when valid parameters are passed.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testexecSCPCommandWhenStringReaderObjectIsReturnedWithCorrectCredentials()
            throws IOException, InterruptedException {
        final StringReader stringReader = clientConnect.execSCPCommand(connect, COMMAND, USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
        if (stringReader == null) {
            fail(STRINGREADER_NOTNULL_EXPECTED_MESSAGE);
        } else {
            assertTrue(STRINGREADER_READY_EXPECTED_MESSAGE, stringReader.ready());
        }
    }
}
