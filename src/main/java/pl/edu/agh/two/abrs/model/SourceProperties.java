package pl.edu.agh.two.abrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "sourceProperties")
@Table(name = "sourceProperties")
public class SourceProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "key", nullable = false)
    private SourcePropertiesType key;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source")
    private Source source;

    public SourceProperties() {
    }

    public SourceProperties(SourcePropertiesType key, String value, Source source) {
        this.key = key;
        this.value = value;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SourcePropertiesType getKey() {
        return key;
    }

    public void setKey(SourcePropertiesType key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "SourceProperties{" +
                "value='" + value + '\'' +
                ", key=" + key +
                ", id=" + id +
                '}';
    }
}
