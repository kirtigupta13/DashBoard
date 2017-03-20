package com.scpdashboard.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.scpdashboard.exception.UserAuthenticationInvalidExeception;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/**
 * Test the {@link SSHClientConnect} class.
 * 
 * @author Kirti Gupta KG048707.
 */
@RunWith(MockitoJUnitRunner.class)
public class SSHClientConnectTest {
    private final SSHClientConnect clientConnect = new SSHClientConnect();

    private static final String HOSTNAME = "fork";
    private static final String USERNAME = "kg048707";
    private static final String PASSWORD = "go";

    private static final String USERNAME_INVALID = "UsernameNotValid";
    private static final String HOSTNAME_INVALID = "HostnameNotValid ";
    private static final String PASSWORD_INVALID = "PasswordNotValid";

    private static final String AUTHENTICATION_INVALID_EXPECTED_MESSAGE = "Authentication with Client SSH was not successful.";
    private static final String USERNAME_INVALID_EXPECTED_MESSAGE = "Inavlid username. Authentication with Client SSH was not successful.";
    private static final String PASSWORD_INVALID_EXPECTED_MESSAGE = "Inavlid password. Authentication with Client SSH was not successful.";
    private static final String HOSTNAME_INVALID_EXPECTED_MESSAGE = "Inavlid hostname. Authentication with Client SSH was not successful.";
    private static final String USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "User Name cannot be null/empty/blank.";
    private static final String HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "Host Name cannot be null/empty/blank.";
    private static final String PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE = "Password cannot be null/empty/blank.";
    private static final String CONNECTION_UNSUCCESSFUL_EXPECTED_MESSAGE = "Connection Unsuccessful.";

    @Mock
    private final SshClient mockSshClient = null;
    private final PasswordAuthenticationClient mockAuthenticationClient = null;;

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when a null user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     */
    @Test
    public void testConnect_userNameNull() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect(null, PASSWORD, HOSTNAME);
            fail(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when empty user name is given.
     *
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_userNameEmpty() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect("", PASSWORD, HOSTNAME);
            fail(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(USERNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when invalid username is given.
     * 
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_userNameInvalid() throws IOException {
        try {
            clientConnect.connect(USERNAME_INVALID, PASSWORD, HOSTNAME);
            fail(USERNAME_INVALID_EXPECTED_MESSAGE);
        } catch (final UserAuthenticationInvalidExeception e) {
            assertEquals(AUTHENTICATION_INVALID_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when empty password is given.
     *
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_passwordNull() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect(USERNAME, null, HOSTNAME);
            fail(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when empty password is given.
     * 
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_passwordEmpty() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect(USERNAME, "", HOSTNAME);
            fail(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(PASSWORD_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when invalid password is given.
     */
    @Test
    public void testConnect_passwordInvalid() throws IOException {
        try {
            clientConnect.connect(USERNAME, PASSWORD_INVALID, HOSTNAME);
            fail(PASSWORD_INVALID_EXPECTED_MESSAGE);
        } catch (final UserAuthenticationInvalidExeception e) {
            assertEquals(AUTHENTICATION_INVALID_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when null hostname is given.
     * 
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_hostNameNull() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect(USERNAME, PASSWORD, null);
            fail(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when empty host name is given.
     * 
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     */
    @Test
    public void testConnect_hostNameEmpty() throws IOException, UserAuthenticationInvalidExeception {
        try {
            clientConnect.connect(USERNAME, PASSWORD, "");
            fail(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE);
        } catch (final IllegalStateException e) {
            assertEquals(HOSTNAME_NOTNULL_NOTEMPTY_EXPECTED_MESSAGE, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} method throws an {@link IllegalArgumentException}
     * when invalid host name is given.
     * 
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     * @throws IOException
     *             if an IO error occurs
     */
    @Test
    public void testConnect_hostNameInvalid() {
        try {
            clientConnect.connect(USERNAME, PASSWORD, HOSTNAME_INVALID);
            fail(HOSTNAME_INVALID_EXPECTED_MESSAGE);
        } catch (final Exception e) {
            assertEquals(HOSTNAME_INVALID, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String, String, String)} open SSHClient , when correct credentials are given.
     * 
     * @throws IllegalStateException
     *
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     */
    @Test
    public void testConnect_Successful() throws IOException, UserAuthenticationInvalidExeception {
        doNothing().when(mockSshClient).connect(HOSTNAME, new IgnoreHostKeyVerification());
        when(mockSshClient.authenticate(mockAuthenticationClient)).thenReturn(AuthenticationProtocolState.COMPLETE);
        final SessionChannelClient session = clientConnect.connect(USERNAME, PASSWORD, HOSTNAME);
        if (session == null) {
            fail(CONNECTION_UNSUCCESSFUL_EXPECTED_MESSAGE);
        } else {
            assertTrue(CONNECTION_UNSUCCESSFUL_EXPECTED_MESSAGE, session.isOpen());
        }
    }
}