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

    @OneToMany(mappedBy = "source", fetch = FetchType.EAGER)
    private List<SourceProperties> sourceProperties;

    public Source() {
    }

    public Source(String name, SourceType sourceType) {
        this.name = name;
        this.sourceType = sourceType;
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
