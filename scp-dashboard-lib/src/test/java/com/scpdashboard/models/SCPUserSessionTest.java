package com.scpdashboard.models;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.sshtools.j2ssh.session.SessionChannelClient;

/**
 * Test the {@link SCPUserSession} class.
 * 
 * @author Kirti Gupta KG048707.
 */
public class SCPUserSessionTest {
    private final static String USERNAME = "SD049814";
    private final static String PASSWORD = "GO";
    private final static String HOSTNAME = "fork";
    private final static String DOMAINNAME = "surround";

    private final SessionChannelClient channelClient = new SessionChannelClient();
    private final SCPUserSession scpUserSession = new SCPUserSession(USERNAME, PASSWORD, HOSTNAME, DOMAINNAME,
            channelClient);

    /**
     * Test ScpUsername {@link SCPUserSession#getScpUsername()} method.
     */
    @Test
    public void testGetScpUsername() {
        assertSame(USERNAME, scpUserSession.getScpUsername());
    }

    /**
     * Test ScpPassword {@link SCPUserSession#getScpPassword()} method.
     */
    @Test
    public void testGetScpPassword() {
        assertSame(PASSWORD, scpUserSession.getScpPassword());
    }

    /**
     * Test ScpHostname {@link SCPUserSession#getScpHostname()} method.
     */
    @Test
    public void testGetScpHostname() {
        assertSame(HOSTNAME, scpUserSession.getScpHostname());
    }

    /**
     * Test ScpDomainName {@link SCPUserSession#getScpDomainName()} method.
     */
    @Test
    public void testGetScpDomianName() {
        assertSame(DOMAINNAME, scpUserSession.getScpDomainName());
    }

    /**
     * Test SessionChannelClient {@link SCPUserSession#getSessionChannelClient()} method.
     */
    @Test
    public void testGetSessionChannelClient() {
        assertSame(channelClient, scpUserSession.getSessionChannelClient());
    }
}