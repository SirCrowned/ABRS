package pl.edu.agh.two.abrs.model;

import java.util.List;

public class GlobalSchemaTable {

    private static int COUNT = 0;

    private int id;

    private String name;

    private List<GlobalSchemaColumn> columns;

    public GlobalSchemaTable() {
        this(null, null);
    }

    public GlobalSchemaTable(String name, List<GlobalSchemaColumn> columns) {
        this.id = COUNT++;
        this.name = name;
        this.columns = columns;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GlobalSchemaColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GlobalSchemaColumn> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("table " + name + " {\n");
        for (GlobalSchemaColumn column : columns) {
            str.append("\t").append(column).append("\n");
        }
        str.append("}");
        return str.toString();
    }
}
