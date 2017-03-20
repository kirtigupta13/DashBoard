package com.scpdashboard.exception;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

import com.scpdashboard.controllers.SSHClientConnect;

/***
 * An {@link Exception} to handle error in {@link SSHClientConnect#connect(String, String, String)} method when invalid user's
 * credentials are passed.
 * 
 * @author Kirti Gupta (KG048707).
 *
 */
public class UserAuthenticationInvalidExeception extends Exception {
    private static final long serialVersionUID = 1L;

    /***
     * Create a new {@link UserAuthenticationInvalidExeception} if invalid, null, blank, or empty user name/password/host name is
     * provided.
     * 
     * @param message
     *            {@link String} containing the error message.
     * @throws IllegalArgumentException
     *             if message is null, blank, or empty.
     */
    public UserAuthenticationInvalidExeception(final String message) {
        super(getValidMessage(message));
    }

    /***
     * Ensure the message is not null, blank, or empty.
     * 
     * @param message
     *            {@link String} message to check
     * @return message if it is not null, blank, or empty.
     * @throws IllegalArgumentException
     *             if message is null, blank, or empty.
     */
    private static String getValidMessage(final String message) {
        checkArgument(StringUtils.isNotBlank(message), "Message is invalid.");
        return message;
    }

}