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

    private LinkedList<Object> attributes = new LinkedList<>();

    public GlobalSchemaRecord(List<Object> attributes){
        if(attributes==null){
            throw new IllegalArgumentException();
        }
        for(Object o: attributes){
            this.attributes.add(o);
        }
    }

    public List<Object> getAttributes() {
        return new ArrayList<>(attributes);
    }
}
