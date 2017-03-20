package com.scpdashboardREST.exceptions;

/**
 * This exception is responsible for holding error messages for the Api
 * 
 * @author SD049814
 *
 */
public class ResponseErrorException extends RuntimeException {

    private static final long serialVersionUID = -8355340648681373432L;

    private final int status;

    public ResponseErrorException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}