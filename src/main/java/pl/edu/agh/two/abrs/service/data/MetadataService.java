package pl.edu.agh.two.abrs.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.Source;
import pl.edu.agh.two.abrs.repository.SourceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataService {

    @Autowired
    private SourceRepository sourceRepository;

    //TODO: implement services to retrieve metadata of CSV, XML or DB TABLE
    public List<LocalSchemaColumn> getMetadata(long sourceId){

        Source source = sourceRepository.getOne(sourceId);

        List<LocalSchemaColumn> dummy = new ArrayList<LocalSchemaColumn>();
        dummy.add(new LocalSchemaColumn("PRODUCT", ColumnType.STRING, null));
        dummy.add(new LocalSchemaColumn("PRICE", ColumnType.DOUBLE, null));
        dummy.add(new LocalSchemaColumn("QUANTITY", ColumnType.INTEGER, null));

        return dummy;
    }


}
