package com.scpdashboard.api;

import static spark.Spark.get;
import static spark.Spark.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;
import com.scpdashboard.exceptions.GetInstanceServiceException;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboard.service.GetServerInstanceService;

import spark.Spark;

/**
 * Api class which has all the routes for the API calls
 * 
 * @author SahithiDesu (SD049814)
 *
 */
public class Api {

    /**
     * Main Executable which has all implementation for the routes
     * 
     * @param args
     *            there are no args here.
     * @throws ResponseErrorException
     *             Throws when the serverId is not valid or when GetServerInstanceService throws exception.
     * 
     */

    public static void main(String[] args) {
        Gson gson = new Gson();
        int port = Integer.parseInt(args[0]);
        Spark.port(port);
        // Route for accepting get request for retrieving server instances information.
        get("/app/serverinstance/:serverId", "application/json", (req, res) -> {
            String serverId = req.params("serverId");
            GetServerInstanceService instanceservice = new GetServerInstanceService();
            List<ServerInstanceInfo> instances = Collections.emptyList();
            try {
                instances = instanceservice.getServerInstance(serverId);
            } catch (GetInstanceServiceException ex) {
                throw new ResponseErrorException(422, ex.getMessage());
            }
            // safe check to see whether the List is not empty. Returns false when the list is null
            if (CollectionUtils.isNotEmpty(instances)) {
                return instances;
            }

            return new ResponseErrorException(404, "No ServerInstances with serverId '%s' found" + serverId);

        }, gson::toJson);

        // transforms the Api error messages in to a valid json format so that the error messages gets displayed to the REST service
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

    }
}