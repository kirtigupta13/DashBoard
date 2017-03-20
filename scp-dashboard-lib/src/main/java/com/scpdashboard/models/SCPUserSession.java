package com.scpdashboard.models;

import com.sshtools.j2ssh.session.SessionChannelClient;

/**
 * 
 * Represents the SCP Client User which contains user name, password, domain name, host name and sessionChannelClient.
 * 
 * @author Kirti Gupta(KG048707).
 */
public class SCPUserSession {

    private final String scpUsername;
    private final String scpPassword;
    private final String scpHostname;
    private final String scpDomainName;
    private final SessionChannelClient sessionChannelClient;

    /**
     * Initializes an instance of {@link SCPUserSession}.
     * 
     * @param username
     *            The user name of the SCP client.
     * @param password
     *            The password of the SCP client.
     * @param hostname
     *            The host name of the SCP client.
     * @param domainName
     *            The domain name of the SCP client.
     * @param SessionChannelClient
     *            The SessionChannelClient which is used in SCP client.
     */
    public SCPUserSession(final String scpUsername, final String scpPassword, final String scpHostname, final String scpDomainName,
            final SessionChannelClient sessionChannelClient) {
        this.scpUsername = scpUsername;
        this.scpPassword = scpPassword;
        this.scpHostname = scpHostname;
        this.scpDomainName = scpDomainName;
        this.sessionChannelClient = sessionChannelClient;
    }

    /**
     * Returns the User Name of the SCP client.
     * 
     * @return String with User Name.
     */
    public String getScpUsername() {
        return scpUsername;
    }

    /**
     * Returns the Password of the SCP client.
     * 
     * @return String with Password.
     */
    public String getScpPassword() {
        return scpPassword;
    }

    /**
     * Returns the Host Name of the SCP client.
     * 
     * @return String with Host name.
     */
    public String getScpHostname() {
        return scpHostname;
    }

    /**
     * Returns the Domain Name of the SCP client.
     * 
     * @return String with Domain name.
     */
    public String getScpDomainName() {
        return scpDomainName;
    }

    /**
     * Returns the SessionChannelClient of the SCP client.
     * 
     * @return {@link SessionChannelClient}.
     */
    public SessionChannelClient getSessionChannelClient() {
        return sessionChannelClient;
    }
}