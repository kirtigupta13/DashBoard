package com.scpdashboardREST.service;

import java.io.IOException;

import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.sshutils.SSHClientConnect;
import com.scpdashboardREST.models.AuthenticationResponse;

/**
 * This service class is responsible for taking the credentials and authenticating by calling the connect function.
 * 
 * @author SD049814
 *
 */
public class AuthenticationService {

    // Note: Created this state for passing this to the Services which uses Execute command
    private static SCPUserSession session = null;

    public static SCPUserSession getSession() {
        return session;
    }

    public static void setSession(SCPUserSession session) {
        AuthenticationService.session = session;
    }

    private final static int HTTP_OK = 200;
    private final static int HTTP_UNAUTHORIZED = 401;
    private final static int HTTP_INTERNAL_ERROR = 500;

    /**
     * This function is responsible for accepting the credentials and returning the authentication Response based on the result that
     * Connect function gives.
     * 
     * @param scpUsername
     * @param scpPassword
     * @param hostname
     * @param domain
     * @param sshUsername
     * @param sshPassword
     * @return AuthenticationResponse, Object which holds authentication status and status message.
     */
    public AuthenticationResponse authenticate(final String scpUsername, final String scpPassword, final String hostname,
            final String domain, final String sshUsername, final String sshPassword) {

        try {
            session = new SSHClientConnect().connect(scpUsername, scpPassword, hostname, domain, sshUsername, sshPassword);
        } catch (UserAuthenticationInvalidExeception | IOException e) {
            String exceptionName = e.getClass().getSimpleName();
            if (exceptionName.contains("Exeception") || exceptionName.contains("Exception")) {
                exceptionName = exceptionName.replaceAll("Exeception", "");// out if input String and finnaly is out..
                exceptionName = exceptionName.replaceAll("Exception", "");
            }
            return (new AuthenticationResponse(HTTP_UNAUTHORIZED, exceptionName + ", " + e.getMessage()));
        } catch (final Throwable e) {
            return (new AuthenticationResponse(HTTP_INTERNAL_ERROR, e.getMessage()));

        }

        // the connection was successful. Prepares a successful response
        return (new AuthenticationResponse(HTTP_OK, ""));
    }
}