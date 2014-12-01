package pl.edu.agh.two.abrs.model;

public class SourceColumn {
    private String name;
    private ColumnType type;

    public SourceColumn(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }
}
