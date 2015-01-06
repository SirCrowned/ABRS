package pl.edu.agh.two.abrs.model.global;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GlobalSchemaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "record_id"))
    private List<String> values = new LinkedList<>();
    
    public GlobalSchemaRecord(List<String> values){
        if(values ==null){
            throw new IllegalArgumentException();
        }
        for(Object o: values){
            this.values.add(o.toString());
        }
    }

    public GlobalSchemaRecord() {

    }

    public List<String> getValues() {
        return new LinkedList<>(values);
    }

    public void setValue(int index, String value) {
        if (value == null || index >= values.size()) {
            throw new IllegalArgumentException();
        }
        values.set(index, value);
    }
}
