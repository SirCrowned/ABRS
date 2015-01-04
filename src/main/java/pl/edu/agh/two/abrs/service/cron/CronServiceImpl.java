package pl.edu.agh.two.abrs.service.cron;

import it.sauronsoftware.cron4j.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.RowItem;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.model.SourceColumn;
import pl.edu.agh.two.abrs.model.SourceProperties;
import pl.edu.agh.two.abrs.model.SourceType;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaRecord;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.model.mapping.Mapping;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRecordRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;
import pl.edu.agh.two.abrs.repository.MappingRepository;
import pl.edu.agh.two.abrs.service.DataTransformingService;
import pl.edu.agh.two.abrs.service.DataTransformingServiceImpl;
import pl.edu.agh.two.abrs.service.csv.CsvReadException;
import pl.edu.agh.two.abrs.service.csv.CsvService;
import pl.edu.agh.two.abrs.service.db.ConnectionParams;
import pl.edu.agh.two.abrs.service.db.DbReaderException;
import pl.edu.agh.two.abrs.service.db.DbReaderService;
import pl.edu.agh.two.abrs.service.db.MySqlConnectionParams;
import pl.edu.agh.two.abrs.service.operator.IdentityOperator;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CronServiceImpl implements CronService {

    @Autowired
    private LocalSchemaRepository localSchemaRepository;

    @Autowired
    private GlobalSchemaRepository globalSchemaRepository;

    @Autowired
    private GlobalSchemaRecordRepository globalSchemaRecordRepository;

    @Autowired
    private MappingRepository mappingRepository;

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private GlobalSchemaTableRepository globalSchemaTableRepository;

    @Autowired
    private DataTransformingService dataTransformingService;

    @PostConstruct
    public void init() {
        dataTransformingService = new DataTransformingServiceImpl(Arrays.asList(new IdentityOperator()));
        startService();
    }

    @Override
    public void startService() {

        Scheduler scheduler = new Scheduler();
        List<LocalSchema> sourceList = localSchemaRepository.findAll();

        for (LocalSchema schema : sourceList) {
            long minutes = schema.getSource().getRefreshInterval();
            String schedulingPattern = String.format("*/%d * * * *", minutes);
            scheduler.schedule(schedulingPattern, new RefreshingTask(schema.getSource()));
        }
        scheduler.start();
    }

    @Override
    public void addRefreshingTask(Source source) {
        Scheduler scheduler = new Scheduler();
        long minutes = source.getRefreshInterval();
        String schedulingPattern = String.format("*/%d * * * *", minutes);
        scheduler.schedule(schedulingPattern, new RefreshingTask(source));
        scheduler.start();
    }

    public class RefreshingTask implements Runnable {

        private Source source;

        private String host, database, password, username, url, table;

        private int port;

        public RefreshingTask(Source source) {
            this.source = source;
        }

        @Override
        public void run() {

            extractParameters();
            List<Row> rows = new ArrayList<Row>();

            if (source.getSourceType() == SourceType.DATABASE) {

                ConnectionParams params = new MySqlConnectionParams(host, port, database, username, password);
                try {
                    rows = dbReaderService.readTable(params, table);
                } catch (DbReaderException e) {
                    e.printStackTrace();
                }
            } else if (source.getSourceType() == SourceType.CSV) {
                try {
                    List<SourceColumn> columns = csvService.getColumns(url);
                    Map<String, ColumnType> columnMap = new HashMap<>();
                    for (SourceColumn column : columns) {
                        columnMap.put(column.getName(), column.getType());
                    }
                    rows = csvService.getData(url, columnMap);
                } catch (CsvReadException e) {
                    e.printStackTrace();
                }
            }

            //tranformation

            GlobalSchema globalSchema = globalSchemaRepository.findAll().get(0);
            synchronized (this) {
                for (Row row : rows) {
                    for (RowItem item : row.getFields()) {
                        String globalColumn = findMapping(item);
                        if (globalColumn == null) {
                            continue;
                        }
                        for (GlobalSchemaTable globalSchemaTable : globalSchema.getTables()) {
                            for (GlobalSchemaColumn globalSchemaColumn : globalSchemaTable.getColumns()) {
                                if (globalSchemaColumn.getName().equals(globalColumn)) {
                                    update(rows, row, item, globalSchemaTable, globalSchemaColumn);
                                }
                            }
                        }
                    }
                }
                globalSchemaTableRepository.save(globalSchema.getTables());
            }
        }

        private void update(List<Row> rows, Row row, RowItem item, GlobalSchemaTable globalSchemaTable, GlobalSchemaColumn globalSchemaColumn) {

            int index = globalSchemaTable.getColumns().indexOf(globalSchemaColumn);
            int columnSize = 0;

            for (GlobalSchemaRecord globalSchemaRecord : globalSchemaTable.getRecords()) {
                if (columnSize < rows.size() && globalSchemaRecord.getValues().get(index).equals("")) {
                    if (columnSize > rows.indexOf(row)) {
                        return;
                    }
                    globalSchemaRecord.setValue(index, item.getValue().toString());
                    globalSchemaRecordRepository.save(globalSchemaRecord);
                    return;
                } else {
                    columnSize++;
                }
            }

            if (rows.indexOf(row) < columnSize) {
                return;
            }

            if (globalSchemaTable.getRecords().size() < rows.size()) {
                List<String> values = new ArrayList<String>();
                for (GlobalSchemaColumn tableColumn : globalSchemaTable.getColumns()) {
                    if (tableColumn.getName().equals(globalSchemaColumn.getName())) {
                        values.add(item.getValue().toString());
                    } else {
                        values.add("");
                    }
                }
                GlobalSchemaRecord record = new GlobalSchemaRecord(values);
                globalSchemaRecordRepository.save(record);
                globalSchemaTable.addRecord(record);
            }
        }

        private String findMapping(RowItem item) {
            for (Mapping mapping : mappingRepository.findAll()) {
                LocalSchemaColumn localSchemaColumn = mapping.getLocalSchemaColumn();
                if (item.getColumnLabel().equals(localSchemaColumn.getName())) {
                    GlobalSchemaColumn globalColumn = mapping.getGlobalSchemaColumn();
                    return globalColumn.getName();
                }
            }
            return null;
        }

        private void extractParameters() {
            for (SourceProperties sourceProperty : source.getSourceProperties()) {
                switch (sourceProperty.getKey()) {
                    case HOST:
                        host = sourceProperty.getValue();
                        break;
                    case PORT:
                        port = Integer.parseInt(sourceProperty.getValue());
                        break;
                    case DATABASE:
                        database = sourceProperty.getValue();
                        break;
                    case PASSWORD:
                        password = sourceProperty.getValue();
                        break;
                    case URL:
                        url = sourceProperty.getValue();
                        break;
                    case USER:
                        username = sourceProperty.getValue();
                        break;
                    case TABLE:
                        table = sourceProperty.getValue();
                        break;
                }
            }
        }
    }
}
