package pl.edu.agh.two.abrs.model.global;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GlobalSchemaTable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToMany(orphanRemoval = true)
    private List<GlobalSchemaColumn> columns;

    private String name;

    public GlobalSchemaTable(List<GlobalSchemaColumn> columns) {
        if(columns==null){
            throw new IllegalArgumentException();
        }
        this.columns = columns;
    }

    public List<GlobalSchemaColumn> getColumns() {
        return new ArrayList<>(columns);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
