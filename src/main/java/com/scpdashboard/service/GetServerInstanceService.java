package com.scpdashboard.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import com.scpdashboard.DAOImpl.InstanceParserDAOImpl;
import com.scpdashboard.dao.ParserDAO;
import com.scpdashboard.exceptions.GetInstanceServiceException;
import com.scpdashboard.exceptions.NotValidServerFileException;
import com.scpdashboard.models.ServerInstanceInfo;

/**
 * Service which is called when Rest API is been called to get the list of instances Information for the given server id
 * 
 * @author SD049814
 *
 */
public class GetServerInstanceService {

    /**
     * Function which returns the list of instances if the valid server id is given.
     * 
     * This makes call to the Execute command which gets the information that is been printed on the backend servers console in to
     * the StringReader.
     * 
     * Parser takes the StringReader to analyze the console output and it internally takes care whether the command is valid or not.
     * It throws exceptions depending on the type of the content that has been displayed on the console.
     * 
     * Note: Right now we have not integrated with the Execute command. This service as of now uses a static file as input. This
     * function needs to be changed when Execute command code gets merged in to stable.
     * 
     * 
     * @param serverId
     * @return List<ServerInstanceInfo>
     * @throws GetInstanceServiceException
     *             Throws when call to the parser throws any exception.
     */
    public List<ServerInstanceInfo> getServerInstance(String serverId) throws GetInstanceServiceException {

        List<ServerInstanceInfo> instances = Collections.emptyList();

        try {
            instances = new InstanceParserDAOImpl()
                    .parseFile(new BufferedReader(new FileReader(new File("src/main/resources/ServerInformation.txt"))));
        } catch (NumberFormatException e) {
            throw new GetInstanceServiceException("Caught NumberFormatException" + e.getMessage());

        } catch (FileNotFoundException e) {
            throw new GetInstanceServiceException("Caught FileNotfound Exception" + e.getMessage());
        } catch (IOException e) {
            throw new GetInstanceServiceException("Caught IOException" + e.getMessage());
        } catch (NotValidServerFileException e) {
            throw new GetInstanceServiceException("Caught NotValidServerFile Exception" + e.getMessage());
        }
        return instances;

    }
}