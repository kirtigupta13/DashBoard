package com.scpdashboard.sshutils;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.models.SSHClientConnectConstants;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/***
 * This class represents the SSH Client connection which hold the user's credentials.
 * 
 * @author Kirti Gupta (KG048707).
 */
public class SSHClientConnect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SCPExecuteCommand.class);

    /**
     * The connect function establishes a connection to the SSH Client which is started by authentication protocol after a
     * successful authentication.
     * 
     * @param scpUsername
     *            Represents the user name of the SCP Client.
     * @param scpPassword
     *            Represents the password of the SCP Client.
     * @param hostname
     *            Represents the host name for SCP dash-board.
     * @param domain
     *            Represents the domain name for SCP dash-board.
     * @param sshUsername
     *            Represents the user name of the SSH Client.
     * @param sshPassword
     *            Represents the password of the SSH Client.
     * @return {@link SCPUserSession} with the SCP credentials and SessionChannelClient.
     * @throws IOException
     *             If an IO error occurs reading the message.
     * @throws IllegalArgumentException
     *             when scpUsername is null/empty/blank.
     * @throws IllegalArgumentException
     *             when scpPassword is null/empty/blank.
     * @throws IllegalArgumentException
     *             when host name is null/empty/blank.
     * @throws IllegalArgumentException
     *             when domain name is null/empty/blank.
     * @throws IllegalArgumentException
     *             when sshUsername is null/empty/blank.
     * @throws IllegalArgumentException
     *             when sshPassword is null/empty/blank.
     * @throws UserAuthenticationInvalidExeception
     *             when Ssh user name and Ssh password are not valid.
     * @throws UnknownHostException
     *             when host name is not valid.
     */
    public SCPUserSession connect(final String scpUsername, final String scpPassword, final String hostname, final String domain,
            final String sshUsername, final String sshPassword)
            throws IOException, UserAuthenticationInvalidExeception, UnknownHostException {
        return connect(scpUsername, scpPassword, hostname, domain, sshUsername, sshPassword, new PasswordAuthenticationClient(),
                new SshClient(), new IgnoreHostKeyVerification());
    }

    SCPUserSession connect(final String scpUsername, final String scpPassword, final String hostname, final String domain,
            final String sshUsername, final String sshPassword, final PasswordAuthenticationClient authenticationClient,
            final SshClient sshClient, final IgnoreHostKeyVerification hostKeyVerification)
            throws IOException, UserAuthenticationInvalidExeception, UnknownHostException {

        Preconditions.checkArgument(StringUtils.isNotBlank(scpUsername), SSHClientConnectConstants.USERNAME_INVALID_ERROR_MSG);
        Preconditions.checkArgument(StringUtils.isNotBlank(scpPassword), SSHClientConnectConstants.PASSWORD_INVALID_ERROR_MSG);
        Preconditions.checkArgument(StringUtils.isNotBlank(hostname), SSHClientConnectConstants.HOSTNAME_INVALID_ERROR_MSG);
        Preconditions.checkArgument(StringUtils.isNotBlank(domain), SSHClientConnectConstants.DOMAIN_INVALID_ERROR_MSG);
        Preconditions.checkArgument(StringUtils.isNotBlank(sshUsername), SSHClientConnectConstants.USERNAME_INVALID_ERROR_MSG);
        Preconditions.checkArgument(StringUtils.isNotBlank(sshPassword), SSHClientConnectConstants.PASSWORD_INVALID_ERROR_MSG);

        sshClient.connect(hostname, hostKeyVerification);
        authenticationClient.setUsername(sshUsername);
        authenticationClient.setPassword(sshPassword);
        final int authStatus = sshClient.authenticate(authenticationClient);
        if (authStatus != AuthenticationProtocolState.COMPLETE) {
            LOGGER.error(SSHClientConnectConstants.AUTHENTICATION_INVALID_ERROR_MSG);
            throw new UserAuthenticationInvalidExeception(SSHClientConnectConstants.AUTHENTICATION_INVALID_ERROR_MSG);
        }
        return new SCPUserSession(scpUsername, scpPassword, hostname, domain, sshClient.openSessionChannel());
    }
}