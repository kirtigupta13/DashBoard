package com.scpdashboardREST.exceptions;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

public class InvalidUserCredentialsException extends Exception {

    private static final long serialVersionUID = 23456;

    public InvalidUserCredentialsException(final String message) {
        super(getValidMessage(message));
    }

    private static String getValidMessage(final String message) {
        checkArgument(StringUtils.isNotBlank(message), "Message is invalid.");
        return message;
    }

}
