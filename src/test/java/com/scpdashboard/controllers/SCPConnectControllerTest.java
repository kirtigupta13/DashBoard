package com.scpdashboard.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.scpdashboard.exception.UserAuthenticationInvalidExeception;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;

public class SCPConnectControllerTest {
    final SCPConnectController scpconnectController = new SCPConnectController();
    SessionChannelClient sessionChannelClient = null;
    SSHClientConnect clientConnect = null;

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
    private final static String HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "Host Name cannot be null/empty/blank.";
    private static final String SESSIONCHANNELCLIENT_NOTNULL_EXPECTED_MESSAGE = "sessionChannel cannot be null.";
    private static final String STRINGREADER_NOTNULL_EXPECTED_MESSAGE = "invalid execute command is passed";
    private static final String STRINGREADER_READY_EXPECTED_MESSAGE = "StringReader object stream is ready to be read.";

    @Mock
    SessionChannelClient mockSessionChannelClient;
    @Mock
    ChannelOutputStream mockChannelOutputStream;
    @Mock
    StringReader mockStringReader;

    @Before
    public void setUp() throws IOException, UserAuthenticationInvalidExeception {
        clientConnect = new SSHClientConnect();
        sessionChannelClient = clientConnect.connect("kg048707", "go", "fork");
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
    public void testexecSCPCommandWhenUserNameIsNull()
            throws InterruptedException, IOException, UserAuthenticationInvalidExeception {
        try {
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, null, DOMAINNAME, PASSWORD, HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, "", DOMAINNAME, PASSWORD, HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, DOMAINNAME, null, HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, DOMAINNAME, "", HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, null, PASSWORD, HOSTNAME);
            ;
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
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, "", PASSWORD, HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, null, USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
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
            scpconnectController.execSCPCommand(sessionChannelClient, "", USERNAME, DOMAINNAME, PASSWORD, HOSTNAME);
            fail(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when empty hostname is given.
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
    public void testexecSCPCommandWhenHostNameIsNull()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, DOMAINNAME, PASSWORD, null);
            fail(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#execSCPCommand(SessionChannelClient,String, String, String)} method throws an
     * {@link IllegalStateException} when a empty hostname is given.
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
    public void testexecSCPCommandWhenHostNameIsEmpty()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            scpconnectController.execSCPCommand(sessionChannelClient, COMMAND, USERNAME, DOMAINNAME, PASSWORD, "");
            fail(COMMAND_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
            throw e;
        }
    }
    // @Test
    // public final void test() throws IOException {
    // final String scpCommand = "echo \'" + USERNAME + "\n" + DOMAINNAME + "\n" + PASSWORD + "\n " + COMMAND
    // + "\' | /cerner/w_standard/" + DOMAINNAME + "/aixrs6000/scpview " + HOSTNAME + "\n";
    // doNothing().when((mockSessionChannelClient).startShell());
    // when((mockSessionChannelClient.getOutputStream())).thenReturn(mockChannelOutputStream);
    // doNothing().when(mockChannelOutputStream).write(scpCommand.getBytes());
    // when(mockStringReader.());
    //
    // }

}
