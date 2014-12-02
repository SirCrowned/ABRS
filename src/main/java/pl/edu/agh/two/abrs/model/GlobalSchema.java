package pl.edu.agh.two.abrs.model;

import java.util.ArrayList;
import java.util.List;

public class GlobalSchema {

    private List<GlobalSchemaTable> tables = new ArrayList<>();

    private List<GlobalSchemaRelation> relations = new ArrayList<>();

    public List<GlobalSchemaTable> getTables() {
        return tables;
    }

    public List<GlobalSchemaRelation> getRelations() {
        return relations;
    }
}
