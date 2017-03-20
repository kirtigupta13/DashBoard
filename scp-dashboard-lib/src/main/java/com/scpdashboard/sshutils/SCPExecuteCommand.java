package com.scpdashboard.sshutils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.models.SSHClientConnectConstants;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionOutputReader;

/**
 * This class represents the execution of the SCP commands in the SCP view. It return the output stream as String.
 * 
 * @author Kirti Gupta (KG048707).
 */
public class SCPExecuteCommand {

    /**
     * The execute command takes SCP-command and scpCredencials. It returns the output string as a String.
     * 
     * @param command
     *            Represent the SCP command executed in SCP-view.
     * @param credentialsWrapper
     *            Represents the object of {@link SCPUserSession} class.
     * @throws IOException
     *             If authentication has not been completed, the server refuses to open the channel or a general IO error occurs.
     * @throws InterruptedException
     *             Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or
     *             during the activity.
     * @throws IllegalArgumentException
     *             when command is null/empty/blank.
     * @throws UserAuthenticationInvalidExeception
     *             Thrown when SCPUserSession or Session ChannelClient is null.
     * @return Output stream as String.
     */
    public String execSCPCommand(final String command, final SCPUserSession scpUserSession)
            throws IOException, InterruptedException, UserAuthenticationInvalidExeception {
        if (scpUserSession == null) {
            throw new UserAuthenticationInvalidExeception(SSHClientConnectConstants.SCPUSERSESSION_NOTNULL_EXCEPTION_MESSAGE);
        }
        if (scpUserSession.getSessionChannelClient() == null) {
            throw new UserAuthenticationInvalidExeception(SSHClientConnectConstants.SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE);
        }
        return execSCPCommand(command, scpUserSession, new SessionOutputReader(scpUserSession.getSessionChannelClient()));
    }

    String execSCPCommand(final String command, final SCPUserSession scpUserSession, final SessionOutputReader sessionOutputReader)
            throws IOException, InterruptedException, UserAuthenticationInvalidExeception {
        Preconditions.checkArgument(StringUtils.isNotBlank(command), SSHClientConnectConstants.COMMAND_INVALID_ERROR_MSG);

        final String scpCommand = scpCommandBuilder(command, scpUserSession);
        scpUserSession.getSessionChannelClient().startShell();
        final ChannelOutputStream channelOutputStream = scpUserSession.getSessionChannelClient().getOutputStream();
        channelOutputStream.write(scpCommand.getBytes());

        Thread.currentThread();
        Thread.sleep(1000 * 2);

        channelOutputStream.close();
        return sessionOutputReader.getOutput();
    }

    /**
     * It represents a String which is passed into execute the commands in the SCP-view.
     * 
     * @param command
     *            Represent the SCP command executed in SCP-view.
     * @param credentialsWrapper
     *            Represents the object of {@link SCPUserSession} class.
     */
    private String scpCommandBuilder(final String command, final SCPUserSession credentialsWrapper) {
        return "echo \'" + credentialsWrapper.getScpUsername() + "\n" + credentialsWrapper.getScpDomainName() + "\n"
                + credentialsWrapper.getScpPassword() + "\n " + command + "\' | scpview " + credentialsWrapper.getScpHostname()
                + "\n";
    }
}