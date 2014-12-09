package pl.edu.agh.two.abrs.service.cron;

import it.sauronsoftware.cron4j.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CronServiceImpl implements CronService {

    @Autowired
    private LocalSchemaRepository localSchemaRepository;

    @PostConstruct
    public void init() {
        startService();
    }

    @Override
    public void startService() {

        Scheduler scheduler = new Scheduler();
        List<LocalSchema> sourceList = localSchemaRepository.findAll();

        for (LocalSchema schema : sourceList) {
            long minutes = schema.getSource().getRefreshInterval();
            String schedulingPattern = String.format("*/%d * * * *", minutes);
            scheduler.schedule(schedulingPattern, new RefreshingTask(schema));
        }
        scheduler.start();
    }
}
