package com.scpdashboardREST.models;

/**
 * This class is responsible for creating AuthenticationResponse object based on the state of the authentication.
 * 
 * @author SD049814
 *
 */
public class AuthenticationResponse {
    private int authStatus;
    private String faultMesage;

    // written this to forbidden the use of default constructor.
    private AuthenticationResponse() {
    }

    public AuthenticationResponse(int status, String authStatusMesaage) {
        this.authStatus = status;
        this.faultMesage = authStatusMesaage;
    }

    /**
     * Getter to get the Authentication Status
     * 
     * @return AuthenticationStatus
     */
    public int getStatus() {
        return authStatus;
    }

    /**
     * Getter to get the Authentication Message
     * 
     * @return
     */
    public String getBody() {
        return faultMesage;
    }

}