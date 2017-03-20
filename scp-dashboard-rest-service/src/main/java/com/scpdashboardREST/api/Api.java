package com.scpdashboardREST.api;

import static spark.Spark.get;
import static spark.Spark.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboardREST.exceptions.ResponseErrorException;
import com.scpdashboardREST.service.AuthenticationService;

import static spark.Spark.before;
import static spark.Spark.port;
import static spark.Spark.after;

/**
 * API class which has all the routes for the API calls
 * 
 * @author SahithiDesu (SD049814)
 *
 */
public class Api {
    public static final int PORT = 8080;

    /**
     * Main Executable which has all implementation for the routes
     * 
     * @param args
     *            there are no args here.
     * 
     */

    public static void main(String[] args) {
        Gson gson = new Gson();
        port(PORT);
        // Route for accepting get request for retrieving server instances information.
        get("/app/serverinstance/:serverId", "application/json", (req, res) -> {
            List<ServerInstanceInfo> instances = new ServerInstancesRetrieverHandler().serverInstancesHandler(req, res,
                    AuthenticationService.getSession());
            return instances;
        }, gson::toJson);

        // transforms the API error messages in to a valid json format so that the error messages gets displayed to the REST service
        // consumer.
        exception(ResponseErrorException.class, (exc, req, res) -> {
            ResponseErrorException err = (ResponseErrorException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));
        });

        // this is being called before every request(GET, PUT, POST, DELETE). Calls handler for authentication
        before((request, response) -> {
            AuthenticationFilter authFilter = new AuthenticationFilter();
            authFilter.authenticationFilterHandler(request, response);
        });

        /**
         * After-filters are evaluated after each request, and can read the request and read/modify the response. This clears the
         * state of the Session Wrapper since the life time for this state should be only for a particular transaction.
         */
        after((request, response) -> {
            AuthenticationService.setSession(null);
        });
    }
}