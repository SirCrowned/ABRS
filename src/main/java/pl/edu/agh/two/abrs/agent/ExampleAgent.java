package pl.edu.agh.two.abrs.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by lopiola on 05.11.14.
 */

// Simple agent that waits for messages and prints them out.
public class ExampleAgent extends Agent {
    protected void setup() {
        // Required to receive O2A objects
        setEnabledO2ACommunication(true, 100);
        addBehaviour(new MyBehaviour(this));
    }

    class MyBehaviour extends CyclicBehaviour {
        Agent agent;

        public MyBehaviour(Agent a) {
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
