package com.scpdashboardREST.exceptions;

import static com.google.common.base.Preconditions.checkArgument;
import org.apache.commons.lang3.StringUtils;

public class GetInstanceServiceException extends Exception {

    private static final long serialVersionUID = -1239942424437182680L;

    /***
     * Create a new {@link GetInstanceServiceException} if Parser code in the library throws exception.
     * 
     * @param message
     *            {@link String} containing the error message.
     * 
     */
    public GetInstanceServiceException(final String message) {
        super(getValidMessage(message));
    }

    /***
     * Ensure the message is not null, blank, or empty.
     * 
     * @param message
     *            {@link String} message to check
     * @return message if it is not null, blank, or empty.
     */
    private static String getValidMessage(final String message) {
        checkArgument(StringUtils.isNotBlank(message), "Message is invalid.");
        return message;
    }

}