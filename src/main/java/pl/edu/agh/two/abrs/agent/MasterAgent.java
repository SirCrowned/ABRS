package pl.edu.agh.two.abrs.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 * Created by lopiola on 05.11.14.
 */

// MasterAgent gives tasks to SlaveAgents for evaluation and collects their responses.
public class MasterAgent extends Agent {
    private AID slaves[];

    protected void setup() {
        Object[] args = getArguments();
        try {
            slaves = (AID[]) args[0];
        } catch (Exception e) {
            throw new RuntimeException("Wrong arguments for " + this.getName());
        }
        AgentPlatform.info("MasterAgent started with " + slaves.length + " slave agents.");
        // Required to receive O2A objects
        setEnabledO2ACommunication(true, 100);
        addBehaviour(new MasterBehaviour(this));
    }

    class MasterBehaviour extends CyclicBehaviour {
        Agent agent;

        public MasterBehaviour(Agent a) {
            super(a);
            agent = a;
        }

        public void action() {
            // Get a message from the O2A mailbox
            Object content = agent.getO2AObject();

            if (content != null) {
                System.out.println(getName() + " - got message: " + content);
            } else {
                // Wait for next messages
                block();
            }
        }


    }
}
