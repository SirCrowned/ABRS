package pl.edu.agh.two.abrs.service.localSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.controller.LocalSchemaController;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.repository.LocalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;
import pl.edu.agh.two.abrs.repository.SourceRepository;
import pl.edu.agh.two.abrs.service.operator.ConcatOperator;
import pl.edu.agh.two.abrs.service.operator.DiffOperator;
import pl.edu.agh.two.abrs.service.operator.DivOperator;
import pl.edu.agh.two.abrs.service.operator.IdentityOperator;
import pl.edu.agh.two.abrs.service.operator.MulOperator;
import pl.edu.agh.two.abrs.service.operator.Operator;
import pl.edu.agh.two.abrs.service.operator.SumOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalSchemaService {

    @Autowired
    private LocalSchemaRepository localSchemaRepository;

    @Autowired
    private LocalSchemaColumnRepository localSchemaColumnRepository;

    @Autowired
    private SourceRepository sourceRepository;

    public boolean addLocalSchema(String name, long sourceId, List<LocalSchemaColumn> columns) {
        Source source = sourceRepository.getOne(sourceId);
        LocalSchema localSchema = new LocalSchema(name, source);
        localSchema = localSchemaRepository.save(localSchema);

        List<LocalSchemaColumn> savedColumns = new ArrayList<>();

        for(LocalSchemaColumn column : columns){
            column.setLocalSchema(localSchema);
            savedColumns.add(localSchemaColumnRepository.save(column));
        }

        localSchema.setLocalSchemaColumn(savedColumns);
        localSchema = localSchemaRepository.saveAndFlush(localSchema);

        return localSchema != null;
    }

    public void removeLocalSchema(long id) {
        localSchemaRepository.delete(id);
    }

    public boolean editLocalSchema(long localSchemaId, List<LocalSchemaController.EditRequest.Column> columns) {
        LocalSchema localSchema = localSchemaRepository.getOne(localSchemaId);
        List<String> existingColumns = columns.stream().map(column -> column.name).collect(Collectors.toList());
        List<LocalSchemaColumn> removeColumns = localSchema.getLocalSchemaColumn().stream().filter(column -> !existingColumns.contains(column.getName())).collect(Collectors.toList());

        for(LocalSchemaColumn column : removeColumns) {
            localSchema.getLocalSchemaColumn().remove(column);
            localSchemaColumnRepository.delete(column);
        }

        for(LocalSchemaController.EditRequest.Column column : columns){
            LocalSchemaColumn local = localSchema.removeColumn(column.name);

            if (local == null ){
                local = new LocalSchemaColumn();
            }

            local.setName(column.name);
            local.setSourceName(column.sourceName);
            local.setType(ColumnType.valueOf(column.type));
            local.setTransformation(getOperator(column.transformation));
            local.setLocalSchema(localSchema);
            localSchema.getLocalSchemaColumn().add(local);

            localSchemaColumnRepository.save(local);
        }

        localSchema = localSchemaRepository.saveAndFlush(localSchema);

        return localSchema != null;
    }

    private Operator getOperator(String name) {
        switch (name) {
            case "concat":
                return new ConcatOperator();
            case "diff":
                return new DiffOperator();
            case "dif":
                return new DivOperator();
            case "identity":
                return new IdentityOperator();
            case "mul":
                return new MulOperator();
            case "sum":
                return new SumOperator();
        }

        return null;
    }
}
