package com.scpdashboard.api;

/**
 * ResponseError is a Exception we use to hold error messages for the Api
 * 
 * @author SD049814
 *
 */
public class ResponseErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final int status;

    public ResponseErrorException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
