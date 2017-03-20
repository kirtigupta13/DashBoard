package com.scpdashboardrestintegration.apitest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import com.scpdashboardREST.api.AuthenticationFilter;
import com.scpdashboardREST.exceptions.ResponseErrorException;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import spark.HaltException;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    private final static String correctSCPUserName = "SD049814";
    private final static String correctScpPassword = "go";
    private final static String correcthostname = "fork";
    private final static String correctdomain = "Surround";
    private final static String correctsshUsername = "kg048707";
    private final static String correctsshPassword = "go";
    private final static String incorrectValue = "incorrect";
    private final static int HTTP_UNAUTHORIZED = 401;

    private AuthenticationFilter authenticationFilter;

    private Set<String> headers;

    @Mock
    private Request mockRequest;

    @Mock
    private Response mockResponse;

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        authenticationFilter = new AuthenticationFilter();
        headers = new HashSet<String>();
        headers.add("scpUsername");
        headers.add("scpPassword");
        headers.add("hostname");
        headers.add("domain");
        headers.add("sshUsername");
        headers.add("sshPassword");

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it doesn't throw any exception when all the proper credentials are given
     */
    @Test
    public void testAuthenticationWithProperCredentials() {

        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
        } catch (ResponseErrorException e) {
            fail("ResponseErrorException should not been thrown as the user credentials are correct");
        } catch (HaltException e) {
            fail("The spark should not throw any Halt Exception for Correct credentials");
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when the headers are null
     */
    @Test
    public void testAuthenticationWithNoHeaders() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(null);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("Headers are not availble or they are empty");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("No header information avaiable"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when the headers are null
     */
    @Test
    public void testAuttestAuthenticationWithNoSCPUserNameHeader() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("SCPUserNameHeader is not provided.");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header scpUsername is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when there is no SCPPasswordHeader
     */
    @Test
    public void testAuttestAuthenticationWithNoSCPPasswordHeader() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("ScpPasswordHeader has not been provided");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header scpPassword is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when there is no hostname
     */
    @Test
    public void testAuttestAuthenticationWithNoHostnameHeader() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("Hostname header has not been provided");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header hostname is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when there is no sshUserNameHeader
     */
    @Test
    public void testAuttestAuthenticationWithNosshUserNameHeader() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("The sshUserNameHeader is not been provided.");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header sshUsername is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException when there is no sshPasswordHeader
     */
    @Test
    public void testAuttestAuthenticationWithNosshPasswordHeader() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("The sshPasswordHeader is not given");

        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header sshPassword is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws ResponseErrorException with incorrect sshUserName
     */
    @Test
    public void testAuttestAuthenticationWithIncorrectsshUserName() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(incorrectValue);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("Incorrect ssh username is been provided");
        } catch (ResponseErrorException e) {
            assertThat(e.getStatus(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getMessage(), equalTo("UserAuthenticationInvalid, Authentication with Client SSH was not successful."));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws ResponseErrorException with incorrect sshPassword
     */
    @Test
    public void testAuttestAuthenticationWithIncorrectsshPassword() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(incorrectValue);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("Incorrect ssh Password is been provided");
        } catch (ResponseErrorException e) {
            assertThat(e.getStatus(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getMessage(), equalTo("UserAuthenticationInvalid, Authentication with Client SSH was not successful."));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws ResponseErrorException with incorrect hostname
     */
    @Test
    public void testAuttestAuthenticationWithIncorrectHostname() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(incorrectValue);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("Incorrect hostName is been provided");
        } catch (ResponseErrorException e) {
            assertThat(e.getStatus(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getMessage(), equalTo("UnknownHost, " + incorrectValue));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException with empty hostname
     */
    @Test
    public void testAuttestAuthenticationWithEmptyHostname() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn("");
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(correctsshUsername);
        when(mockRequest.headers("sshPassword")).thenReturn(correctsshPassword);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
            fail("empty hostname is been provided");
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header hostname is not found"));
        }

    }

    /**
     * Test {@link AuthenticationFilter.java} to check that it throws HaltException with sshUsername and sshPassword as Null
     */
    @Test
    public void testAuttestAuthenticationWithssHUserNameAndPasswordAsNull() {
        when(mockRequest.requestMethod()).thenReturn("GET");
        when(mockRequest.headers()).thenReturn(headers);
        when(mockRequest.headers("scpUsername")).thenReturn(correctSCPUserName);
        when(mockRequest.headers("scpPassword")).thenReturn(correctScpPassword);
        when(mockRequest.headers("hostname")).thenReturn(correcthostname);
        when(mockRequest.headers("domain")).thenReturn(correctdomain);
        when(mockRequest.headers("sshUsername")).thenReturn(null);
        when(mockRequest.headers("sshPassword")).thenReturn(null);

        try {
            authenticationFilter.authenticationFilterHandler(mockRequest, mockResponse);
        } catch (HaltException e) {
            assertThat(e.getStatusCode(), equalTo(HTTP_UNAUTHORIZED));
            assertThat(e.getBody(), equalTo("Header sshUsername is not found"));
        }

    }

}