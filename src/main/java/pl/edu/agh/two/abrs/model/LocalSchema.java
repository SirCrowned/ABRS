package pl.edu.agh.two.abrs.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "localSchema")
@Table(name = "localSchema")
public class LocalSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.DETACH)
    private Source source;

    @OneToMany(mappedBy = "localSchema", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<LocalSchemaColumn> localSchemaColumn;

    public LocalSchema() {
    }

    public LocalSchema(String name, Source source) {
        this.name = name;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<LocalSchemaColumn> getLocalSchemaColumn() {
        return localSchemaColumn;
    }

    public void setLocalSchemaColumn(List<LocalSchemaColumn> columns){
        this.localSchemaColumn = columns;
    }

    @Override
    public String toString() {
        return "LocalSchema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source=" + source +
                '}';
    }
}
