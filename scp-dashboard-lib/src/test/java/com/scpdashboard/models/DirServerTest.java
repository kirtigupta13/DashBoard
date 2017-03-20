package com.scpdashboard.models;

import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Test the {@link DirServer} class.
 * 
 * @author Kirti Gupta KG048707.
 */
public class DirServerTest {
    private final static int SCPSERVERID = 1;
    private final static String SCPDESCRIPTION = "Service Manager";

    private final DirServer dirServerWrapper = new DirServer(SCPSERVERID, SCPDESCRIPTION);

    /**
     * Test {@link DirServer#getScpServerID()} method.
     */
    @Test
    public void testGetScpServerID() {
        assertSame(SCPSERVERID, dirServerWrapper.getScpServerID());
    }

    /**
     * Test {@link DirServer#getscpDescription()} method.
     */
    @Test
    public void testGetScpDescription() {
        assertSame(SCPDESCRIPTION, dirServerWrapper.getScpServerDescription());
    }
}