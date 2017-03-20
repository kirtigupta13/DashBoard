package com.scpdashboard.models;

/**
 * Model which stores all the information about the Server instances i.e: The Instance Id, description of the ServerInstance, number
 * of messages the ServerInstace has and the state of the server(Running,Aborted,Starting)
 * 
 * @author SD049814
 *
 */

public class ServerInstanceInfo {

    private int instanceId;
    private String description;
    private int numberOfMsgs;
    private String state;

    public ServerInstanceInfo(int instanceId, String description, int numberOfMsgs, String state) {
        this.instanceId = instanceId;
        this.description = description;
        this.numberOfMsgs = numberOfMsgs;
        this.state = state;
    }

    public String toString() {
        return "InstanceId: " + this.instanceId + "\nDescription: " + this.description + "\nnumberOfMessages" + this.numberOfMsgs
                + "\nstate" + this.state;
    }

}
