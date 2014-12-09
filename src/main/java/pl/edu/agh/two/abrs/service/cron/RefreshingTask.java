package pl.edu.agh.two.abrs.service.cron;

import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.model.SourceProperties;

import java.util.List;

public class RefreshingTask implements Runnable {

    private final LocalSchema schema;

    public RefreshingTask(LocalSchema schema) {
        this.schema = schema;
    }

    @Override
    public void run() {

        List<SourceProperties> properties = schema.getSource().getSourceProperties();

        System.out.println("<mock>updating data from " + schema.getName());

        //TODO: ABRS-43
        // service to pulling localSchema data

        // service to migrate localSchema data to globalSchema

        //TODO: ABRS-49
        // service to update globalSchema data

    }
}
