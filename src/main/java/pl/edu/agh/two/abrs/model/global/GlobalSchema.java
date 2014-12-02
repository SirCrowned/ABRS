package pl.edu.agh.two.abrs.model.global;


import javax.annotation.Generated;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GlobalSchema {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(orphanRemoval = true)
    private List<GlobalSchemaTable> tables;

    public GlobalSchema(){}

    public GlobalSchema(List<GlobalSchemaTable> tables) {
        if(tables==null){
            throw new IllegalArgumentException();
        }
        this.tables = tables;
    }

    public List<GlobalSchemaTable> getTables() {
        return new ArrayList<>(tables);
    }
}
