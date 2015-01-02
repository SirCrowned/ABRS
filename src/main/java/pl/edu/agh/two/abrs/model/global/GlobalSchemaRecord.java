package pl.edu.agh.two.abrs.model.global;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GlobalSchemaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ElementCollection
    @CollectionTable(joinColumns=@JoinColumn(name="record_id"))
    private List<String> values = new LinkedList<>();

    public GlobalSchemaRecord(List<String> values){
        if(values ==null){
            throw new IllegalArgumentException();
        }
        for(Object o: values){
            this.values.add(o.toString());
        }
    }

    public List<String> getValues() {
        return new LinkedList<>(values);
    }
}
