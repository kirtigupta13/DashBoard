package com.scpdashboard.DAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.scpdashboard.dao.ParserDAO;
import com.scpdashboard.exceptions.NotValidServerFileException;

import com.scpdashboard.models.ServerInstanceInfo;

/**
 * An implementation of the Instance Parser which Parsers the Server Instance Information data that has been retrieved after
 * executing SCP commands through ssh.
 * 
 * The data on which this Parser parses is obtained by executing the following command : printf
 * "$Username\n$domain\n$password\n server -entry $serverentryId" | $cer_exe/scpview fork
 * 
 * This Parser looks for the table header("id description  msgs  state") in the file and decides whether the given file has valid
 * information or not. This is the point were the Parser analyzes each line to extract necessary information. i.e Server instance
 * information.
 * 
 * This Parser expects the actual Instance information to start with the Number(The instance Id). If it does not find, it throws
 * NumberFormatException
 * 
 * Note: The Parser assumes that file formats are only of type .txt as of now. Will have to work on making the Parser more robust to
 * different file formats in future.
 * 
 * @author SD049814
 *
 */
public class InstanceParserDAOImpl implements ParserDAO<ServerInstanceInfo> {

    @Override
    public List<ServerInstanceInfo> parseFile(BufferedReader bufferedReader)
            throws IOException, NumberFormatException, NotValidServerFileException {
        if (bufferedReader == null) {
            throw new NullPointerException("BufferedReaderObject Object is Null");
        }
        String line;
        final List<ServerInstanceInfo> instances = new ArrayList<ServerInstanceInfo>();
        int startConsideringLineFlag = 0;

        while ((line = bufferedReader.readLine()) != null) {
            // checks for instance header information. Until then it just ignores each line with out analyzing
            if (line.startsWith("id")) {
                startConsideringLineFlag = 1;
                continue;
            }
            // It reaches this point when it decides that it is the valid file to extract Server information
            // removing some unwanted lines after the header
            if (startConsideringLineFlag == 0 || line.startsWith("-") || line.equals("")) {
                continue;
            }
            // splitting the line to extract instance Information
            final String[] stringarray = line.split("\\s{2,}");
            final int id = Integer.parseInt(stringarray[0]);
            final String description = stringarray[1];
            final int noOfMessages = Integer.parseInt(stringarray[2]);
            final String state = stringarray[3];
            instances.add(new ServerInstanceInfo(id, description, noOfMessages, state));
        }
        // Throws an exception telling that the file given does not have valid server data.
        if (startConsideringLineFlag == 0) {

            throw new NotValidServerFileException();

        }
        return (instances);
    }
}
