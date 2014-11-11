package pl.edu.agh.two.abrs.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 * Created by lopiola on 05.11.14.
 */
public class ExampleAgent extends Agent {
    protected void setup()
    {
        addBehaviour( new MyBehaviour( this ) );
    }


    class MyBehaviour extends CyclicBehaviour
    {
        Agent agent;
        public MyBehaviour(Agent a) {
            super(a);
            agent = a;
        }

        public void action()
        {
            System.out.println("I'm alive!");
            try {
                agent.wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
