package com.scpdashboardrestintegration.testutil;

/**
 * Class which holds the information of the Response(status and body) of the Api call
 * 
 * @author SD049814
 *
 */
public class TestApiResponse {
    private final int status;
    private final String body;

    public TestApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    /**
     * Getter to get the Http Status
     * 
     * @return HttpStatus
     */
    public int getStatus() {
        return status;
    }

    /**
     * Getter to get the body
     * 
     * @return body, string as json
     */
    public String getBody() {
        return body;
    }
}