package pl.edu.agh.two.abrs.model.global;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GlobalSchemaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LinkedList<Object> values = new LinkedList<>();

    public GlobalSchemaRecord(List<Object> values){
        if(values ==null){
            throw new IllegalArgumentException();
        }
        for(Object o: values){
            this.values.add(o);
        }
    }

    public List<Object> getValues() {
        return new ArrayList<>(values);
    }
}
