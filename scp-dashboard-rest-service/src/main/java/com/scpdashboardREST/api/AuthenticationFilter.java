package com.scpdashboardREST.api;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.scpdashboardREST.exceptions.ResponseErrorException;
import com.scpdashboardREST.models.AuthenticationResponse;
import com.scpdashboardREST.service.AuthenticationService;

import spark.Request;
import spark.Response;
import spark.Spark;

/**
 * This class is responsible for handling Authentication Requests for all types of HTTP request methods.
 * 
 * This is been used in Spark.before(Before-filters are evaluated before each request, and can read the request and read/modify the
 * response
 * 
 * @author SD049814
 *
 */

public class AuthenticationFilter {

    public static final int HTTP_UNAUTHORIZED = 401;
    public static final String SCPUSERNAME = "scpUsername";
    public static final String SCPPASSWORD = "scpPassword";
    public static final String SSHUSERNAME = "sshUsername";
    public static final String SSHPASSWORD = "sshPassword";
    public static final String DOMAIN = "domain";
    public static final String HOSTNAME = "hostname";

    /**
     * This function is responsible for validating the required data that has been extracted from the Http header
     * 
     * @param scpUsername
     *            Front end username for the scp application.
     * @param scpPassword
     *            Front end password for the scp application.
     * @param hostname
     *            Hostname for the application.
     * @param domain
     *            Domain name for the scp application.
     * @param sshUsername
     *            Backend username with host as fork
     * @param sshPassword
     *            Backend password with host as fork
     */
    private static void validateHeaderInformation(String scpUsername, String scpPassword, String hostname, String domain,
            String sshUsername, String sshPassword) {
        Preconditions.checkArgument(StringUtils.isNotBlank(scpUsername), "Header scpUsername is not found");
        Preconditions.checkArgument(StringUtils.isNotBlank(scpPassword), "Header scpPassword is not found");
        Preconditions.checkArgument(StringUtils.isNotBlank(hostname), "Header hostname is not found");
        Preconditions.checkArgument(StringUtils.isNotBlank(domain), "Header domain is not found");
        Preconditions.checkArgument(StringUtils.isNotBlank(sshUsername), "Header sshUsername is not found");
        Preconditions.checkArgument(StringUtils.isNotBlank(sshPassword), "Header sshPassword is not found");
    }

    /**
     * This function is responsible for handling all the authentication requests.
     * 
     * @param request
     *            Spark Request object
     * @param response
     *            Spark Response object
     * @throws ResponseErrorException
     *             When wrong user credentials (ssh username,ssh password, domain, hostname) are given @throws
     */

    public void authenticationFilterHandler(Request request, Response response) {

        String method = request.requestMethod();
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE") || method.equals("GET")) {
            Set<String> headers = request.headers();
            if (headers == null) {
                Spark.halt(HTTP_UNAUTHORIZED, "No header information avaiable");
            }
            try {
                validateHeaderInformation(request.headers(SCPUSERNAME), request.headers(SCPPASSWORD), request.headers(HOSTNAME),
                        request.headers(DOMAIN), request.headers(SSHUSERNAME), request.headers(SSHPASSWORD));
            } catch (IllegalArgumentException e) {
                Spark.halt(HTTP_UNAUTHORIZED, e.getMessage());
            }
            AuthenticationResponse authResponse = new AuthenticationService().authenticate(request.headers(SCPUSERNAME),
                    request.headers(SCPPASSWORD), request.headers(HOSTNAME), request.headers(DOMAIN), request.headers(SSHUSERNAME),
                    request.headers(SSHPASSWORD));
            if (authResponse.getStatus() == HTTP_UNAUTHORIZED) {
                throw new ResponseErrorException(HTTP_UNAUTHORIZED, authResponse.getBody());
            }

        }
    }

}