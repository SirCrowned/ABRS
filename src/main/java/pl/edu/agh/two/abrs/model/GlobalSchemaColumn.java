package pl.edu.agh.two.abrs.model;

public class GlobalSchemaColumn {

    private static int COUNT = 0;

    private int id;

    private String name;

    private ColumnType type;

    public GlobalSchemaColumn() {
        this(null, null);
    }

    public GlobalSchemaColumn(String name, ColumnType type) {
        this.id = COUNT++;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " : " + type;
    }
}
