package com.scpdashboardREST.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;

import com.scpdashboard.DAOImpl.InstanceParserDAOImpl;
import com.scpdashboardREST.exceptions.GetInstanceServiceException;
import com.scpdashboardREST.exceptions.ResponseErrorException;
import com.scpdashboard.exceptions.NotValidServerFileException;
import com.scpdashboard.exceptions.UserAuthenticationInvalidExeception;
import com.scpdashboard.models.SCPUserSession;
import com.scpdashboard.models.ServerInstanceInfo;
import com.scpdashboard.sshutils.SCPExecuteCommand;

/**
 * This service is responsible for serving REST API call when ServerInstances are requested.
 * 
 * @author SD049814
 *
 */
public class GetServerInstanceService {

    private static final int HTTP_INTERNAL_ERROR = 500;

    /**
     * This function is responsible for returning the list of instances if the valid server id is given.
     * 
     * This makes call to the Execute command which gets the information that is been printed on the backend servers console in to
     * the StringReader.
     * 
     * Parser takes the StringReader to analyze the console output and it internally takes care whether the command is valid or not.
     * It throws exceptions depending on the type of the content that has been displayed on the console.
     * 
     * This the place where front end credential validation will happen.When wrong credentials are given the Execute command gives a
     * blob of string with irrelevant data and Parser analyzes that data and throws "Not a Valid Server FileException" there by, not
     * allowing the user extract the data
     * 
     * Note: In future, Parser can be modified to generate proper error messages to tell the user that he/she is not authenticated.
     * 
     * @param serverId
     * @param SCPUserSession
     *            The session wrapper object that is been created after connect function is been called.
     * @return List<ServerInstanceInfo>
     * @throws GetInstanceServiceException
     *             Throws when call to the parser and Execute command throws any exception.
     */
    public List<ServerInstanceInfo> getServerInstance(String serverId, SCPUserSession session) throws GetInstanceServiceException {

        List<ServerInstanceInfo> instances = Collections.emptyList();
        final String scpGetInstancesCommand = "server -entry " + serverId;

        String extractedStringFromBackend;
        try {
            extractedStringFromBackend = new SCPExecuteCommand().execSCPCommand(scpGetInstancesCommand, session);
            instances = new InstanceParserDAOImpl().parseFile(new BufferedReader(new StringReader(extractedStringFromBackend)));
        } catch (InterruptedException | NumberFormatException | NotValidServerFileException | UserAuthenticationInvalidExeception
                | IOException e) {
            throw new GetInstanceServiceException(e.getMessage());

        } catch (final Throwable e) {
            throw new ResponseErrorException(HTTP_INTERNAL_ERROR, e.getMessage());
        }

        if (CollectionUtils.isEmpty(instances)) {
            throw new GetInstanceServiceException("There are no instances Present with the serverid " + serverId);
        }
        return instances;

    }
}