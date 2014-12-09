package pl.edu.agh.two.abrs.model.global;

import pl.edu.agh.two.abrs.model.graph.GraphItem;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GlobalSchemaTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GlobalSchemaColumn> columns;

    @OneToOne(cascade = CascadeType.DETACH)
    private GraphItem graphItem;

    private String name;

    public GlobalSchemaTable() {
    }

    public GlobalSchemaTable(String name, List<GlobalSchemaColumn> columns) {
        if (columns == null || name == null) {
            throw new IllegalArgumentException();
        }
        this.columns = columns;
        this.name = name;
    }

    public List<GlobalSchemaColumn> getColumns() {
        return new ArrayList<>(columns);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GraphItem getGraphItem() {
        return graphItem;
    }

    public void setGraphItem(GraphItem graphItem) {
        this.graphItem = graphItem;
    }
}
