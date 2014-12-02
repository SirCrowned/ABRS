package pl.edu.agh.two.abrs.model.global;

import pl.edu.agh.two.abrs.model.ColumnType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GlobalSchemaColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ColumnType columnType;
    private String name;

    public GlobalSchemaColumn(ColumnType columnType, String name) {
        if(columnType == null || name== null){
            throw new IllegalArgumentException();
        }
        this.columnType = columnType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }
}
