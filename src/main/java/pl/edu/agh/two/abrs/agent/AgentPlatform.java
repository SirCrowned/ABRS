package pl.edu.agh.two.abrs.agent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

// A wrapper on JADE API that provides a minimalistic API for agent platform
public class AgentPlatform {
    private final static int JADE_CONTAINER_PORT = 1090;
    private final static String JADE_CONTAINER_NAME = "abrs_platform";

    AgentContainer agentContainer;

    // Starts the agent platform
    public void start(boolean showJADEGUI) throws ControllerException {
        Profile profile = new ProfileImpl("localhost", JADE_CONTAINER_PORT, JADE_CONTAINER_NAME);
        profile.setParameter("gui", showJADEGUI ? "true" : "false"); // Set to true for JADE GUI

        agentContainer = jade.core.Runtime.instance().createMainContainer(profile);
        agentContainer.start();
        info("Agent platform has started.");
    }

    // Creates an agent with given name, of selected type (a class that extends Agent class)
    // and possibly some setup arguments.
    public boolean createNewAgent(String name, String className, Object[] args) {
        try {
            AgentController agentController = agentContainer.createNewAgent(name, className, args);
            agentController.start();
            info("Agent '" + name + "' successfully spawned.");
            return true;
        } catch (ControllerException e) {
            error("Cannot spawn agent '" + name + "': " + e.toString());
            return false;
        }
    }

    // Sends a message (any POJO) to an agent with given name.
    // The agent must enable O2A communication to receive those messages.
    public boolean sendMessage(String name, Object content) {
        try {
            AgentController agentController = agentContainer.getAgent(name);
            agentController.putO2AObject(content, false);
            return true;
        } catch (ControllerException e) {
            error("Cannot send message to agent '" + name + "': " + e.toString());
            return false;
        }
    }

    private void info(String message) {
        System.out.println("[INFO] " + message);
    }

    private void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}
