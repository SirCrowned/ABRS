package pl.edu.agh.two.abrs.model;

import pl.edu.agh.two.abrs.service.operator.Operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "localSchemaColumn")
@Table(name = "localSchemaColumn")
public class LocalSchemaColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sourceName", nullable = false)
    private String sourceName;

    @Column(name = "type", nullable = false)
    private ColumnType type;

    @Column(name = "transformation", nullable = false)
    private String transformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localSchema")
    private LocalSchema localSchema;

    @Transient
    private Operator operator;

    public LocalSchemaColumn() {
    }

    public LocalSchemaColumn(String name, String sourceName, ColumnType type, Operator transformation) {
        this.name = name;
        this.sourceName = sourceName;
        this.type = type;
        this.operator = transformation;
        this.transformation = transformation.name();
    }

    public LocalSchemaColumn(long id, String name, String sourceName, ColumnType type, Operator transformation, LocalSchema localSchema) {
        this.id = id;
        this.name = name;
        this.sourceName = sourceName;
        this.type = type;
        this.operator = transformation;
        this.transformation = transformation.name();
        this.localSchema = localSchema;
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

    public void setName(String name){
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(Operator transformation) {
        this.operator = transformation;
        this.transformation = transformation.name();
    }

    public Operator getOperator() {
        return operator;
    }

    public LocalSchema getLocalSchema() {
        return localSchema;
    }

    public void setLocalSchema(LocalSchema localSchema) {
        this.localSchema = localSchema;
    }

    @Override
    public String toString() {
        return "LocalSchemaColumn{" +
                "name='" + name + '\'' +
                "sourceName='" + sourceName + '\'' +
                ", type=" + type +
                ", transformation=" + transformation +
                ", id=" + id +
                ", localSchema =" + localSchema.getName() +
                '}';
    }
}
