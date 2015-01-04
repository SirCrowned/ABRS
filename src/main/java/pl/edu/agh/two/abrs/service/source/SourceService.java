package pl.edu.agh.two.abrs.service.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.model.SourceProperties;
import pl.edu.agh.two.abrs.model.SourcePropertiesType;
import pl.edu.agh.two.abrs.model.SourceType;
import pl.edu.agh.two.abrs.repository.SourcePropertiesRepository;
import pl.edu.agh.two.abrs.repository.SourceRepository;
import pl.edu.agh.two.abrs.service.cron.CronService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourcePropertiesRepository sourcePropertiesRepository;

    @Autowired
    private CronService cronService;

    public boolean addSourceDatabase(String name, String host, String port, String user, String password, String database, String table) {
        Source source = new Source(name, SourceType.DATABASE);
        source = sourceRepository.save(source);
        List<SourceProperties> sourcePropertiesList = new ArrayList<>();

        SourceProperties hostProp = new SourceProperties(SourcePropertiesType.HOST, host, source);
        hostProp = sourcePropertiesRepository.save(hostProp);
        sourcePropertiesList.add(hostProp);

        SourceProperties portProp = new SourceProperties(SourcePropertiesType.PORT, port, source);
        portProp = sourcePropertiesRepository.save(portProp);
        sourcePropertiesList.add(portProp);

        SourceProperties userProp = new SourceProperties(SourcePropertiesType.USER, user, source);
        userProp = sourcePropertiesRepository.save(userProp);
        sourcePropertiesList.add(userProp);

        SourceProperties passProp = new SourceProperties(SourcePropertiesType.PASSWORD, password, source);
        passProp = sourcePropertiesRepository.save(passProp);
        sourcePropertiesList.add(passProp);

        SourceProperties dbProp = new SourceProperties(SourcePropertiesType.DATABASE, database, source);
        dbProp = sourcePropertiesRepository.save(dbProp);
        sourcePropertiesList.add(dbProp);

        SourceProperties tableProp = new SourceProperties(SourcePropertiesType.TABLE, table, source);
        tableProp = sourcePropertiesRepository.save(tableProp);
        sourcePropertiesList.add(tableProp);

        source.setSourceProperties(sourcePropertiesList);
        source = sourceRepository.saveAndFlush(source);

        cronService.addRefreshingTask(source);

        return source != null;
    }

    public boolean addSourceUrl(String name, SourceType type, String url) {
        Source source = new Source(name, type);
        source = sourceRepository.save(source);
        List<SourceProperties> sourcePropertiesList = new ArrayList<>();

        SourceProperties urlProp = new SourceProperties(SourcePropertiesType.URL, url, source);
        urlProp = sourcePropertiesRepository.save(urlProp);
        sourcePropertiesList.add(urlProp);

        source.setSourceProperties(sourcePropertiesList);
        source = sourceRepository.saveAndFlush(source);

        cronService.addRefreshingTask(source);

        return source != null;
    }

    public void removeSource(long id) {
        sourceRepository.delete(id);
    }
}
