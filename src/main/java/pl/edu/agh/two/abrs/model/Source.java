package pl.edu.agh.two.abrs.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "source")
@Table(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private SourceType sourceType;

    @Column(name = "refreshInterval", nullable = false)
    private long refreshInterval = 1;

    @OneToMany(mappedBy = "source", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SourceProperties> sourceProperties;

    public Source() {
    }

    public Source(String name, SourceType sourceType) {
        this(name, sourceType, 5);
    }

    public Source(String name, SourceType sourceType, long refreshInterval) {
        this.name = name;
        this.sourceType = sourceType;
        this.refreshInterval = refreshInterval;
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

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public List<SourceProperties> getSourceProperties() {
        return sourceProperties;
    }

    public void setSourceProperties(List<SourceProperties> sourceProperties) {
        this.sourceProperties = sourceProperties;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sourceType=" + sourceType +
                ", sourceProperties=" + sourceProperties +
                '}';
    }
}
