package com.scpdashboardREST.api;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboardREST.exceptions.GetInstanceServiceException;
import com.scpdashboardREST.exceptions.ResponseErrorException;
import com.scpdashboardREST.service.GetServerInstanceService;

import spark.Request;
import spark.Response;

/**
 * This class is responsible for handling all the requests that are requesting for Server Instances Information.
 * 
 * @author SD049814
 *
 */

public class ServerInstancesRetrieverHandler {
    private static final int HTTP_UNPROCESSED_ENTITY = 422;

    /**
     * This function is responsible for handling all the requests for Server Instances. It calls the getServerInstance service to
     * fetch all the instances information
     * 
     * @param request
     *            Spark request.
     * @param response
     *            Spark response.
     * @param session
     *            Session Wrapper object that is been generated after doing Authentication.
     * @return List<ServerInstanceInfo> List of ServerInstances objects.
     * 
     * @throws ResponseErrorException
     *             Catches all the error messages and prepares response error message
     */
    public List<ServerInstanceInfo> serverInstancesHandler(Request request, Response response, SCPUserSession session) {
        String serverId = request.params("serverId");
        List<ServerInstanceInfo> instances = Collections.emptyList();
        GetServerInstanceService getServerInstanceObj = new GetServerInstanceService();
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(serverId), "Serverid not given");
            instances = getServerInstanceObj.getServerInstance(serverId, session);

        } catch (GetInstanceServiceException | IllegalArgumentException ex) {
            throw new ResponseErrorException(HTTP_UNPROCESSED_ENTITY, ex.getMessage());
        }
        return instances;
    }

}
