package pl.edu.agh.two.abrs.model.mapping;

import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Mapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    private GlobalSchemaColumn globalSchemaColumn;

    @OneToOne(cascade = CascadeType.DETACH)
    private LocalSchemaColumn localSchemaColumn;

    public Mapping(GlobalSchemaColumn globalSchemaColumn, LocalSchemaColumn localSchemaColumn) {
        this.globalSchemaColumn = globalSchemaColumn;
        this.localSchemaColumn = localSchemaColumn;
    }

    public Mapping() {
    }

    public GlobalSchemaColumn getGlobalSchemaColumn() {
        return globalSchemaColumn;
    }

    public LocalSchemaColumn getLocalSchemaColumn() {
        return localSchemaColumn;
    }

    public Long getId() {
        return id;
    }

}
