package pl.edu.agh.two.abrs.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.model.SourceProperties;
import pl.edu.agh.two.abrs.model.SourcePropertiesType;
import pl.edu.agh.two.abrs.model.SourceType;
import pl.edu.agh.two.abrs.repository.SourceRepository;
import pl.edu.agh.two.abrs.service.db.ConnectionParams;
import pl.edu.agh.two.abrs.service.db.DbReaderException;
import pl.edu.agh.two.abrs.service.db.DbReaderService;
import pl.edu.agh.two.abrs.service.db.MySqlConnectionParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MetadataService {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private DbReaderService dbSourceService;

    //TODO: implement services to retrieve metadata of CSV, XML or DB TABLE
    public List<LocalSchemaColumn> getMetadata(long sourceId) throws DbReaderException {
        Source source = sourceRepository.getOne(sourceId);
        return getColumnsMetadata(source);
    }

    private List<LocalSchemaColumn> getColumnsMetadata(Source source) throws DbReaderException {

        Map<SourcePropertiesType, String> properties = sourcePropertiesToMap(source.getSourceProperties());
        List<LocalSchemaColumn> columns;
        switch(source.getSourceType()){
            case DATABASE:
                columns = dbSourceService.getColumns(
                        convertSourcePropertiesToConnectionParams(properties),
                        properties.get(SourcePropertiesType.TABLE));
                break;
            default:
                columns = new ArrayList<>();
                columns.add(new LocalSchemaColumn("PRODUCT", ColumnType.STRING, null));
                columns.add(new LocalSchemaColumn("PRICE", ColumnType.DOUBLE, null));
                columns.add(new LocalSchemaColumn("QUANTITY", ColumnType.INTEGER, null));
                break;
        }
        return columns;
    }

    private ConnectionParams convertSourcePropertiesToConnectionParams(Map<SourcePropertiesType, String> properties){
        //TODO: add support to other drivers when available
        String host = properties.get(SourcePropertiesType.HOST);
        Integer port = Integer.parseInt(properties.get(SourcePropertiesType.PORT));
        String database = properties.get(SourcePropertiesType.DATABASE);
        String user = properties.get(SourcePropertiesType.USER);
        String password = properties.get(SourcePropertiesType.PASSWORD);

        return new MySqlConnectionParams(host, port, database, user, password);
    }

    private Map<SourcePropertiesType, String> sourcePropertiesToMap(List<SourceProperties> sourceProperties){
        return sourceProperties
                .stream()
                .collect(Collectors.toMap(SourceProperties::getKey, p -> p.getValue()));
    }
}
