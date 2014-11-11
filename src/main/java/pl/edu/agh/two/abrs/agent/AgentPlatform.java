package pl.edu.agh.two.abrs.agent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

/**
 * Created by lopiola on 05.11.14.
 */
public class AgentPlatform {
    public void start() throws ControllerException {
        Profile profile = new ProfileImpl("localhost", 1090, "abrs_platform");
        profile.setParameter("gui", "false"); // Set to true for JADE GUI

        AgentContainer container = jade.core.Runtime.instance().createMainContainer(profile);
        container.start();

        AgentController agentController = container.createNewAgent("Agent",
                ExampleAgent.class.getName(), null);
        agentController.start();

    }
}
