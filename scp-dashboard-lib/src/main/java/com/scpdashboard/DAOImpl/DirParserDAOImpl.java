package com.scpdashboard.DAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.scpdashboard.dao.ParserDAO;
import com.scpdashboard.models.DirServer;
import com.scpdashboard.models.SSHClientConnectConstants;

/**
 * This class represents the Implementation of the Directory Parser. It parses the scpServerID and scpDescription.
 * 
 * @author Kirti Gupta KG048707.
 *
 */
public class DirParserDAOImpl implements ParserDAO<DirServer> {

    @Override
    public List<DirServer> parseFile(final BufferedReader bufferedReader) throws IOException, NumberFormatException {
        Preconditions.checkArgument(bufferedReader != null, SSHClientConnectConstants.BUFFERREADER_NOTNULL_EXCEPTION_MESSAGE);
        boolean hasParsedSuccessfully = false;
        String line;
        final String ID = "id";
        final String SPECIALCHARACTER = "-";
        final String EMPTYSTRING = "";
        String scpServerDescription = "";

        final List<DirServer> dirServers = new ArrayList<DirServer>();

        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith(ID)) {
                hasParsedSuccessfully = true;
                continue;
            }
            if (!hasParsedSuccessfully || line.startsWith(SPECIALCHARACTER) || line.equals(EMPTYSTRING)) {
                continue;
            }
            final String[] dirCommandArrayList = line.split("\\s{2,}");

            final int scpServerID = Integer.parseInt(dirCommandArrayList[0]);
            if (dirCommandArrayList.length == 2) {
                scpServerDescription = dirCommandArrayList[1];
            } else {
                scpServerDescription = "";
            }
            dirServers.add(new DirServer(scpServerID, scpServerDescription));
        }
        return dirServers;
    }
}