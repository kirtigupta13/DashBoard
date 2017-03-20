package com.scpdashboard.sshutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/**
 * Test the {@link SSHClientConnect} class.
 * 
 * @author Kirti Gupta KG048707.
 */
@RunWith(MockitoJUnitRunner.class)
public class SSHClientConnectTest {

    private final static String DOMAINNAME = "surround";
    private final static String HOSTNAME = "fork";
    private final static String SCPUSERNAME = "sd049814";
    private final static String SCPPASSWORD = "GO";

    private final static String SSHUSERNAME = "kg048707";
    private final static String SSHPASSWORD = "go";

    private final static String HOSTNAME_INVALID = "HostnameNotValid ";
    private final static String USERNAME_INVALID_ERROR_MSG = "User Name cannot be null/empty/blank.";
    private final static String PASSWORD_INVALID_ERROR_MSG = "Password cannot be null/empty/blank.";
    private final static String HOSTNAME_INVALID_ERROR_MSG = "Host Name cannot be null/empty/blank.";
    private final static String DOMAIN_INVALID_ERROR_MSG = "Domain name cannot be null/empty/blank.";
    private final static String AUTHENTICATION_INVALID_ERROR_MSG = "Authentication with Client SSH was not successful.";

    private final SSHClientConnect sSHClientConnect = new SSHClientConnect();
    private final SshClient mockSshClient = Mockito.mock(SshClient.class);
    private final IgnoreHostKeyVerification mockHostKeyVerification = mock(IgnoreHostKeyVerification.class);
    private final PasswordAuthenticationClient mockAuthenticationClient = mock(PasswordAuthenticationClient.class);

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} open Session Channel when
     * Authentication completes.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     */
    @Test
    public void testConnect_AuthenticationClientIsComplete() throws IOException, UserAuthenticationInvalidExeception {
        when(mockSshClient.authenticate(mockAuthenticationClient)).thenReturn(AuthenticationProtocolState.COMPLETE);

        final SCPUserSession actual = sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME,
                SSHPASSWORD, mockAuthenticationClient, mockSshClient, mockHostKeyVerification);

        verify(mockSshClient, times(1)).connect(HOSTNAME, mockHostKeyVerification);
        assertSame(SCPUSERNAME, actual.getScpUsername());
        assertSame(SCPPASSWORD, actual.getScpPassword());
        assertSame(HOSTNAME, actual.getScpHostname());
        assertSame(DOMAINNAME, actual.getScpDomainName());
        assertEquals(mockSshClient.openSessionChannel(), actual.getSessionChannelClient());
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} throws
     * UserAuthenticationInvalidExeception when Authentication is not complete.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     */
    @Test
    public void testConnect_AuthenticationClientIsNotComplete() throws Exception {
        try {

            final PasswordAuthenticationClient mockAuthenticationClient = Mockito.mock(PasswordAuthenticationClient.class);

            when(mockSshClient.authenticate(mockAuthenticationClient)).thenReturn(AuthenticationProtocolState.CANCELLED);

            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, SSHPASSWORD,
                    mockAuthenticationClient, mockSshClient, mockHostKeyVerification);
            fail("UserAuthenticationInvalidExeception should have been thrown.");
        } catch (final UserAuthenticationInvalidExeception e) {
            verify(mockSshClient, times(1)).connect(HOSTNAME, mockHostKeyVerification);
            assertEquals(AUTHENTICATION_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null SSH user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHUserNameNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, null, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null ssh user is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHUserNameEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, "", SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a black ssh user is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHUserNameBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, " ", SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null password is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHPasswordNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, null, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(PASSWORD_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a empty ssh password is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHPasswordEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, "", mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(PASSWORD_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a black ssh password is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SSHPasswordBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, " ", mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(PASSWORD_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null hostname is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_HostnameNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, null, DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("UserAuthenticationInvalidExeception should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(HOSTNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a empty hostname is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_HostnameEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, "", DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(HOSTNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a blank hostname is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_HostnameBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, " ", DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(HOSTNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link UserAuthenticationInvalidExeception} when a hostname is invalid.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_HostnameInvalid()
            throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            Mockito.doThrow(UnknownHostException.class).when(mockSshClient).connect(HOSTNAME_INVALID, mockHostKeyVerification);
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME_INVALID, DOMAINNAME, SSHUSERNAME, SSHPASSWORD,
                    mockAuthenticationClient, mockSshClient, mockHostKeyVerification);
            fail("UnknownHostException should have been thrown.");
        } catch (final UnknownHostException e) {
            assertEquals("UnknownHostException", e.getClass().getSimpleName());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a blank domain name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_DomainNameBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, " ", SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(DOMAIN_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a empty domain name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_DomainNameEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, "", SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(DOMAIN_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null domain name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_DomainNameNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(SCPUSERNAME, SCPPASSWORD, HOSTNAME, null, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(DOMAIN_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a null user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SCPUserNameNull() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(null, SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a empty user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SCPUserNameEmpty() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect("", SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }

    /**
     * Tests that the {@link SSHClientConnect#connect(String,String,String,String,String,String )} method throws an
     * {@link IllegalArgumentException} when a blank scp user name is given.
     * 
     * @throws IOException
     *             if an IO error occurs
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is cleared when
     *             this exception is thrown.
     */
    @Test
    public void testConnect_SCPUserNameBlank() throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        try {
            sSHClientConnect.connect(" ", SCPPASSWORD, HOSTNAME, DOMAINNAME, SSHUSERNAME, SSHPASSWORD, mockAuthenticationClient,
                    mockSshClient, mockHostKeyVerification);
            fail("IllegalArgumentException should have been thrown.");
        } catch (final IllegalArgumentException e) {
            assertEquals(USERNAME_INVALID_ERROR_MSG, e.getMessage());
        }
    }
}