package com.scpdashboard.models;

/**
 * 
 * Represents the Dir command server wrapper which contains Dir server id and Dir command server description.
 * 
 * @author Kirti Gupta(KG048707).
 */
public class DirServer {

    private final int scpServerID;
    private final String scpServerDescription;

    /**
     * Initializes an instance of {@link DirServer}.
     * 
     * @param scpServerID
     *            The server id of the SCP client in Dir command.
     * @param scpDescription
     *            The server description of the SCP client in Dir command.
     */
    public DirServer(final int scpServerID, final String scpDescription) {
        this.scpServerID = scpServerID;
        this.scpServerDescription = scpDescription;
    }

    /**
     * Returns the scpServerID of the SCP client.
     * 
     * @return scpServerID with serve ID in Dir command, where scpServerID should not be less than zero.
     */
    public int getScpServerID() {
        return scpServerID;
    }

    /**
     * Returns the scpServerID of the SCP client.
     * 
     * @return scpDescription with serve ID in Dir command, where scpDescription cannot be null/empty/blank .
     */
    public String getScpServerDescription() {
        return scpServerDescription;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((scpServerDescription == null) ? 0 : scpServerDescription.hashCode());
        result = prime * result + scpServerID;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DirServer other = (DirServer) obj;
        if (scpServerDescription == null) {
            if (other.scpServerDescription != null)
                return false;
        } else if (!scpServerDescription.equals(other.scpServerDescription))
            return false;
        if (scpServerID != other.scpServerID)
            return false;
        return true;
    }
}
