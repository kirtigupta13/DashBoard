package com.scpdashboard.controllers;

import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;

import com.cerner.system.i18n.util.ResourceAssistant;
import com.scpdashboard.exception.UserAuthenticationInvalidExeception;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/**
 * This class represents the SSH Client connection which hold the user's credentials.
 * 
 * @author Kirti Gupta (KG048707).
 */
public class SSHClientConnect {

    private final static String USERNAME_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidUserNameExceptionMessage");
    private final static String HOSTNAME_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidHostNameExceptionMessage");
    private final static String PASSWORD_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidPasswordExceptionMessage");
    private final static String AUTHENTICATION_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "AuthenticatedNotSuccessSSHClientExceptionMessage");
    private static final String COMMAND_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidCommandExceptionMessage");
    private static final String DOMAIN_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidDomanNameExceptionMessage");
    private static final String SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE = ResourceAssistant.getString(SSHClientConnect.class,
            "SessionChannelNotNullExceptionMessage");

    /**
     * Evaluate whether the client has successfully authenticated.
     * 
     * @param username
     *            Represents the user name of the SSH Client.
     * @param password
     *            Represents the password of the SSH Client.
     * @param hostname
     *            Represents the host name of the SSH Client.
     * @param sshClient
     *            com.sshtools.j2ssh.SshClient Implements an SSH client with methods to connect to a remote server and perform all
     *            Necessary SSH functions such as SCP, SFTP, executing commands, starting the users shell.
     * @param authenticationClient
     *            Authenticate the user on the remote host.
     * @return Session Channel Client object, which is used in {@link SSHClientConnect#connect(String, String, String)}.
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     */
    SessionChannelClient authenticate(final String username, final String password, final String hostname,
            final SshClient sshClient, final PasswordAuthenticationClient authenticationClient)
            throws IOException, UserAuthenticationInvalidExeception {

        sshClient.connect(hostname, new IgnoreHostKeyVerification());

        authenticationClient.setUsername(username);
        authenticationClient.setPassword(password);
        final int authStatus = sshClient.authenticate(authenticationClient);
        if (authStatus != AuthenticationProtocolState.COMPLETE) {
            throw new UserAuthenticationInvalidExeception(AUTHENTICATION_INVALID_ERROR_MSG);
        }
        return sshClient.openSessionChannel();
    }

    /**
     * The connect function establishes a connection to the SSH Client and it is started by authentication protocol after a
     * successful authentication.
     * 
     * @param username
     *            Represents the user name of the SSH Client.
     * @param password
     *            Represents the password of the SSH Client. ; * @param hostname Represents the host name of the SSH Client.
     * @return Session Channel Client object, which will be used latter in executing the commands and opening a pseudo terminal.
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws UserAuthenticationInvalidExeception
     *             when User name/Password/Host name is not valid.
     */
    public SessionChannelClient connect(final String username, final String password, final String hostname)
            throws IOException, UserAuthenticationInvalidExeception {
        checkState(StringUtils.isNotBlank(username), USERNAME_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(password), PASSWORD_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(hostname), HOSTNAME_INVALID_ERROR_MSG);

        return authenticate(username, password, hostname, new SshClient(), new PasswordAuthenticationClient());
    }

    /**
     * The execute command takes SCP-command and returns the output string as a string reader.
     * 
     * @param sessionChannel
     *            Session Channel Client object, is returned from {@link SSHClientConnect#connect(String, String, String)}.
     * @param command
     *            Represent the SCP command executed in SCP-view.
     * @param username
     *            Represent the Front-end user name of the SCP-view.
     * @param domainName
     *            Represent the Front-end domain name of the SCP-view.
     * @param password
     *            Represent the Front-end password of the SCP-view.
     * @return Output stream as StringReader object.
     * @throws IOException
     *             If authentication has not been completed, the server refuses to open the channel or a general IO error occurs.
     * @throws InterruptedException
     *             Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or
     *             during the activity.
     */
    public StringReader execSCPCommand(final SessionChannelClient sessionChannel, final String command, final String username,
            final String domainName, final String password, final String hostname) throws IOException, InterruptedException {
        if ((sessionChannel == null)) {
            throw new NullPointerException(SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE);
        }
        checkState(StringUtils.isNotBlank(command), COMMAND_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(username), USERNAME_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(password), PASSWORD_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(domainName), DOMAIN_INVALID_ERROR_MSG);
        checkState(StringUtils.isNotBlank(hostname), HOSTNAME_INVALID_ERROR_MSG);
        final String scpCommand = "echo \'" + username + "\n" + domainName + "\n" + password + "\n " + command
                + "\' | /cerner/w_standard/" + domainName + "/aixrs6000/scpview " + hostname + "\n";

        final SessionOutputReader sessionOutputReader = new SessionOutputReader(sessionChannel);

        sessionChannel.startShell();
        sessionChannel.getOutputStream().write(scpCommand.getBytes());

        Thread.currentThread();
        Thread.sleep(1000 * 2);

        return (new StringReader(sessionOutputReader.getOutput()));
    }

    public static void main(final String[] args) throws IOException, UserAuthenticationInvalidExeception, InterruptedException {
        final SSHClientConnect clientConnect = new SSHClientConnect();
        final SessionChannelClient connect = clientConnect.connect("kg048707", "go", "fork");
        System.out.println(connect.getName().toString());
        final StringReader execCommand = clientConnect.execSCPCommand(connect, "server -entry 101", "SD049814", "surround", "GO",
                "fork");
        final BufferedReader in4 = new BufferedReader(execCommand);
        String s = null;
        while ((s = in4.readLine()) != null)
            System.out.println(s);

    }
}