package com.scpdashboard.controllers;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;

import com.cerner.system.i18n.util.ResourceAssistant;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;

public class SCPConnectController {
    private final static String USERNAME_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidUserNameExceptionMessage");
    private final static String HOSTNAME_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidHostNameExceptionMessage");
    private final static String PASSWORD_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidPasswordExceptionMessage");
    private static final String COMMAND_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidCommandExceptionMessage");
    private static final String DOMAIN_INVALID_ERROR_MSG = ResourceAssistant.getString(SSHClientConnect.class,
            "InvalidDomanNameExceptionMessage");
    private static final String SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE = ResourceAssistant.getString(SSHClientConnect.class,
            "SessionChannelNotNullExceptionMessage");

    private static final String DOMAINNAME = "SURROUND";
    private static final String USERNAME = "SD049814";
    private static final String PASSWORD = "GO";
    private static final String COMMAND = "server -entry 101";
    private static final String HOSTNAME = "fork";
    private static StringReader execSCPCommand;

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
        final SessionOutputReader sessionOutputReader = getOutputReader(sessionChannel);

        sessionChannel.startShell();
        final ChannelOutputStream outputStream = sessionChannel.getOutputStream();
        outputStream.write(scpCommand.getBytes());

        Thread.currentThread();
        Thread.sleep(1000 * 2);
        final String k = sessionOutputReader.getOutput();
        return (new StringReader(k));
    }

    public SessionOutputReader getOutputReader(final SessionChannelClient sessionChannel) {
        final SessionOutputReader sessionOutputReader = new SessionOutputReader(sessionChannel);
        return sessionOutputReader;
    }

}
