package pl.edu.agh.two.abrs.service.localSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.repository.LocalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;
import pl.edu.agh.two.abrs.repository.SourceRepository;

import java.util.ArrayList;
import java.util.List;

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



}
