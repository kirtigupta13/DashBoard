package com.scpdashboardrestintegration.apitest;

import org.junit.AfterClass;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboardREST.api.Api;
import com.scpdashboardrestintegration.testutil.TestApiClient;
import com.scpdashboardrestintegration.testutil.TestApiResponse;

import spark.Spark;

/**
 * Integration test class which tests API calls that are made for SCP-Dashboard
 * 
 * @author Sahithi Desu (SD049814)
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiTest {

    public static final String PORT = "8080";
    private TestApiClient client;
    private Gson gson;

    @Mock
    TestApiClient mockClient;

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        String[] args = { PORT };
        Api.main(args);
    }

    @Before
    public void setUp() throws IOException {
        client = new TestApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    /**
     * Test {@link TestApiClient.java} to check whether http response is ok and server instance information is retrived
     */
    @Test
    public void testCanGetServerInstances() {
        List<ServerInstanceInfo> expected_instances = new ArrayList<ServerInstanceInfo>();
        expected_instances.add(new ServerInstanceInfo(147, "Custom 200 server -old version", 0, "running"));
        expected_instances.add(new ServerInstanceInfo(203, "OpsExec_RCA_GSR - Revenue", 0, "running"));
        gson.toJson(expected_instances);
        String method = "GET";
        String url = "/app/serverinstance/101";

        when(mockClient.getRequest(method, url)).thenReturn(new TestApiResponse(200, gson.toJson(expected_instances)));

        TestApiResponse res = mockClient.getRequest(method, url);
        assertEquals("the Response is expected to be ok, but it is not", 200, res.getStatus());

        ServerInstanceInfo[] serverInstances = gson.fromJson(res.getBody(), ServerInstanceInfo[].class);

        assertEquals(2, serverInstances.length);
    }

    /**
     * Test {@link TestApiClient.java} to check that the status is greater than 400 when the url passed doesn't have a proper
     * mapping with the specified routes in the API file
     * 
     */
    @Test
    public void testWhenURLIsNotProper() {
        String error_message = "404 Not found";
        String method = "GET";
        String url = "/JunkUrl";
        when(mockClient.getRequest(method, url)).thenReturn(new TestApiResponse(404, gson.toJson(error_message)));

        TestApiResponse res = mockClient.getRequest("GET", "/JunkUrl");

        assertThat("The Url given doesn't have proper mapping to the api", res.getStatus(), greaterThan(400));
    }

    /**
     * Test {@link TestApiClient.java} to check whether it throws any runtime exception when the string url is null
     */
    @Test
    public void testWhenURIiSNull() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("There is an Connection error");

        TestApiResponse res = client.getRequest("GET", null);
    }

    /**
     * Test {@link TestApiClient.java} to check whether it throws any Runtime exception when the Method is not specified
     */
    @Test
    public void testWhenMethodIsNull() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("There is an Connection error");

        TestApiResponse res = client.getRequest(null, null);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

}
