package pl.edu.agh.two.abrs.model.global;


import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

@Entity
public class GlobalSchema {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<GlobalSchemaColumn> columns;

    public GlobalSchema(){}

    public GlobalSchema(List<GlobalSchemaColumn> columns) {
        if(columns==null){
            throw new IllegalArgumentException();
        }
        this.columns = columns;
    }

    public List<GlobalSchemaColumn> getColumns() {
        return columns;
    }
}
