package pl.edu.agh.two.abrs.service.cron;

import pl.edu.agh.two.abrs.model.Source;

public interface CronService {

    public abstract void startService();

    public abstract void addRefreshingTask(Source source);
}
