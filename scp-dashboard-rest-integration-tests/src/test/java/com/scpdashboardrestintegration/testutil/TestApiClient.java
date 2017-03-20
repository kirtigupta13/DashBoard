package com.scpdashboardrestintegration.testutil;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestApiClient {
    private String server;

    public TestApiClient(String server) {
        this.server = server;
    }

    /**
     * getRequest function which creates a URL Object and connects
     * 
     * @param method
     *            {@link String} Accepts HTTP methods
     * @param uri
     *            {@link String} Path that we specify to the routes
     * @throws RuntimeException
     *             If the connection is not been established properly.
     * @return TestApiResponseObject which has the information about the status and response body
     */
    public TestApiResponse getRequest(String method, String uri) {
        try {
            URL url = new URL(server + uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            InputStream inputStream = connection.getResponseCode() < 400 ? connection.getInputStream()
                    : connection.getErrorStream();
            String body = IOUtils.toString(inputStream);
            return new TestApiResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There is an Connection error");
        }
    }
}