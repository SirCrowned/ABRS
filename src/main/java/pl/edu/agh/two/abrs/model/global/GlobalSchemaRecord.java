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

    public GlobalSchemaRecord(List<Object> attributes){
        if(attributes==null){
            throw new IllegalArgumentException();
        }
        for(Object o: attributes){
            this.attributes.add(o.toString());
        }
    }

    public List<Object> getAttributes() {
        return new ArrayList<>(attributes);
    }
}
