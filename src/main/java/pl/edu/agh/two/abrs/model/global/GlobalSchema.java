package pl.edu.agh.two.abrs.model.global;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GlobalSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GlobalSchemaTable> tables;

    public GlobalSchema() {
    }

    public GlobalSchema(List<GlobalSchemaTable> tables) {
        if (tables == null) {
            throw new IllegalArgumentException();
        }
        this.tables = tables;
    }

    public List<GlobalSchemaTable> getTables() {
        return new ArrayList<>(tables);
    }

    public Long getId() {
        return id;
    }

    public void addTable(GlobalSchemaTable globalSchemaTable) {
        tables.add(globalSchemaTable);
    }

    public void removeTable(String tableName) {
        new ArrayList<>(tables).stream().filter(table -> table.getName().equals(tableName)).forEach(tables::remove);
    }
}
