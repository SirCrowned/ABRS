package pl.edu.agh.two.abrs.model.global;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GlobalSchemaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ElementCollection
    @CollectionTable(joinColumns=@JoinColumn(name="record_id"))
    private List<String> attributes = new LinkedList<>();

    public GlobalSchemaRecord(List<String> attributes){
        if(attributes==null){
            throw new IllegalArgumentException();
        }
        for(Object o: attributes){
            this.attributes.add(o.toString());
        }
    }

    public List<String> getAttributes() {
        return new LinkedList<>(attributes);
    }
}
