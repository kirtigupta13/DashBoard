package com.scpdashboard.exceptions;

/***
 * An {@link Exception} to handle user defined exceptions.
 * 
 * @author Kirti Gupta (KG048707).
 *
 */
 public class UserAuthenticationInvalidExeception extends Exception {

    /**
     * serialVersionUID is used as a version control in a Serializable class.
     */
    private static final long serialVersionUID = 1913302798802843568L;

    /***
     * Create a new {@link UserAuthenticationInvalidExeception} with a string message.
     * 
     * @param message
     *            {@link String} containing the error message.
     */
    public UserAuthenticationInvalidExeception(final String message) {
        super(message);
    }
}