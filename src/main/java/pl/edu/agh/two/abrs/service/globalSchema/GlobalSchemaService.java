package pl.edu.agh.two.abrs.service.globalSchema;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.repository.GlobalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GlobalSchemaService {

    @Autowired
    private GlobalSchemaRepository gsRepository;

    @Autowired
    private GlobalSchemaColumnRepository columnRepository;

    @Autowired
    private GlobalSchemaTableRepository tableRepository;

    public GlobalSchema getGlobalSchema() {
        List<GlobalSchema> globalSchemas = gsRepository.findAll();
        return globalSchemas.size() == 0 ? null : globalSchemas.get(0);
    }

    public void updateGlobalSchema(GlobalSchema globalSchema) {
        if (globalSchema == null) {
            throw new IllegalArgumentException();
        }
        gsRepository.deleteAll();
        for (GlobalSchemaTable schemaTable : globalSchema.getTables()) {
            columnRepository.save(schemaTable.getColumns());
        }
        tableRepository.save(globalSchema.getTables());

        gsRepository.save(globalSchema);
    }

    public void deleteGlobalSchema() {
        gsRepository.deleteAll();
    }

    public List<Pair<Object, Object>> get2ColumnsFromTable(String tableName, String colName1, String colName2) {
        GlobalSchemaTable table = tableRepository.findFirstByName(tableName);

//        TODO(vucalur): DRY (1)
        List<Integer> requestedColsIndices = getColumnsIndices(table, colName1, colName2);
        List<ColumnType> requestedColsTypes = getColumnTypes(table, requestedColsIndices);

        List<Pair<Object, Object>> result = table.getRecords().stream()
                .map(record -> Pair.of(
                             record.getValues().get(requestedColsIndices.get(0)),
                             record.getValues().get(requestedColsIndices.get(1))
                     )
                ).map(pairOfStrReprs -> Pair.of(
                              requestedColsTypes.get(0).fromString(pairOfStrReprs.getLeft()),
                              requestedColsTypes.get(1).fromString(pairOfStrReprs.getRight())
                      )
                ).collect(Collectors.toList());
        return result;
    }

    private List<Integer> getColumnsIndices(GlobalSchemaTable table, String colName1, String colName2) {
        List<Integer> result = new ArrayList<>(2);
        int i = 0;
        for (GlobalSchemaColumn col : table.getColumns()) {
            String colName = col.getName();
            if (colName.equals(colName1) || colName.equals(colName2)) {
                result.add(i);
            }
            i++;
        }

        if (result.size() != 2) {
            throw new IllegalArgumentException("Requested columns not found");
        }

        return result;
    }

    private List<ColumnType> getColumnTypes(GlobalSchemaTable table, List<Integer> requestedColsIndices) {
        List<ColumnType> result = new ArrayList<>(2);
        requestedColsIndices.stream().forEach(i -> {
            ColumnType type = table.getColumns().get(i).getColumnType();
            result.add(type);
        });
        return result;
    }

    public List<Row> getTableRecords(String tableName) {
        GlobalSchemaTable table = tableRepository.findFirstByName(tableName);

//        TODO(vucalur): DRY (2)
        List<Integer> requestedColsIndices = IntStream.range(0, table.getColumns().size()).boxed()
                .collect(Collectors.toList());
        List<ColumnType> requestedColsTypes = getColumnTypes(table, requestedColsIndices);

        List<Row> result = table.getRecords().stream()
                .map(record -> {
                    int i = 0;
                    List<Object> values = new ArrayList<>();
                    for (String strRepr : record.getValues()) {
                        values.add(requestedColsTypes.get(i++).fromString(strRepr));
                    }
                    return new Row(values);
                }).collect(Collectors.toList());
        return result;
    }

    public Row getTableColumnNames(String tableName) {
        GlobalSchemaTable table = tableRepository.findFirstByName(tableName);
        List<Object> columnNames1 = table.getColumns().stream()
                .map(col -> col.getName())
                .collect(Collectors.toList());
        return new Row(columnNames1);
    }
}
