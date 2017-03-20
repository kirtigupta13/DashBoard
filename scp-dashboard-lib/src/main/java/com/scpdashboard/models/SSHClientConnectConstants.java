package com.scpdashboard.models;

/**
 * Represents the User Exception Message used in {@link SCPUserSession}.
 * 
 * @author Kirti Gupta (KG048707).
 *
 */
public class SSHClientConnectConstants {
    public final static String USERNAME_INVALID_ERROR_MSG = "User Name cannot be null/empty/blank.";
    public final static String HOSTNAME_INVALID_ERROR_MSG = "Host Name cannot be null/empty/blank.";
    public final static String PASSWORD_INVALID_ERROR_MSG = "Password cannot be null/empty/blank.";
    public final static String DOMAIN_INVALID_ERROR_MSG = "Domain name cannot be null/empty/blank.";
    public final static String COMMAND_INVALID_ERROR_MSG = "Command cannot be null/empty/blank.";
    public final static String SESSIONCHANNELCLIENT_NOTNULL_EXCEPTION_MESSAGE = "Error establishing connection.SessionChannel cannot be null.";
    public final static String AUTHENTICATION_INVALID_ERROR_MSG = "Authentication with Client SSH was not successful.";
    public final static String SCPUSERSESSION_NOTNULL_EXCEPTION_MESSAGE = "Error establishing connection.Scp User Session cannot be null.";
}