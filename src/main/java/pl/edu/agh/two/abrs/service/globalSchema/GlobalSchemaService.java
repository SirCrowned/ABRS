package pl.edu.agh.two.abrs.service.globalSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.repository.GlobalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;

import java.util.List;

@Service
public class GlobalSchemaService {

    @Autowired
    private GlobalSchemaRepository globalSchemaRepository;

    @Autowired
    private GlobalSchemaColumnRepository globalSchemaColumnRepository;

    @Autowired
    private GlobalSchemaTableRepository globalSchemaTableRepository;

    public GlobalSchemaRepository getGlobalSchemaRepository() {
        return globalSchemaRepository;
    }

    public GlobalSchema getGlobalSchema(){
        List<GlobalSchema> globalSchemas = globalSchemaRepository.findAll();
        return globalSchemas.size() == 0? null : globalSchemas.get(0);
    }

    public void updateGlobalSchema(GlobalSchema globalSchema){
        if(globalSchema==null){
            throw new IllegalArgumentException();
        }
        globalSchemaRepository.deleteAll();
        for (GlobalSchemaTable schemaTable : globalSchema.getTables()) {
                globalSchemaColumnRepository.save(schemaTable.getColumns());
        }
        globalSchemaTableRepository.save(globalSchema.getTables());

        globalSchemaRepository.save(globalSchema);
    }

    public void deleteGlobalSchema(){
        globalSchemaRepository.deleteAll();
    }
}
