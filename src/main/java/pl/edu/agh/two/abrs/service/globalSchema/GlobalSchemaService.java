package pl.edu.agh.two.abrs.service.globalSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRepository;

import java.util.List;

@Service
public class GlobalSchemaService {

    @Autowired
    private GlobalSchemaRepository globalSchemaRepository;

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
        globalSchemaRepository.save(globalSchema);
    }

    public void deleteGlobalSchema(){
        globalSchemaRepository.deleteAll();
    }
}
