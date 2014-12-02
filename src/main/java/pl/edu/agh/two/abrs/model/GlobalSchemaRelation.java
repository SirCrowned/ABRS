package pl.edu.agh.two.abrs.model;

public class GlobalSchemaRelation {

    private String fromTable;

    private String fromColumn;

    private String toTable;

    private String toColumn;

    public GlobalSchemaRelation() {
    }

    public GlobalSchemaRelation(String fromTable, String fromColumn, String toTable, String toColumn) {
        this.fromTable = fromTable;
        this.fromColumn = fromColumn;
        this.toTable = toTable;
        this.toColumn = toColumn;
    }

    public String getFromTable() {
        return fromTable;
    }

    public void setFromTable(String fromTable) {
        this.fromTable = fromTable;
    }

    public String getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(String fromColumn) {
        this.fromColumn = fromColumn;
    }

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    public String getToColumn() {
        return toColumn;
    }

    public void setToColumn(String toColumn) {
        this.toColumn = toColumn;
    }

    public String getFrom() {
        return fromTable + "." + fromColumn;
    }

    public void setFrom(String from) {
        this.fromTable = from.split("\\.")[0];
        this.fromColumn = from.split("\\.")[1];
    }

    public String getTo() {
        return toTable + "." + toColumn;
    }

    public void setTo(String to) {
        this.toTable = to.split("\\.")[0];
        this.toColumn = to.split("\\.")[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GlobalSchemaRelation)) {
            return false;
        }

        GlobalSchemaRelation that = (GlobalSchemaRelation) o;

        return fromColumn.equals(that.fromColumn) && fromTable.equals(that.fromTable) && toColumn.equals(that.toColumn) && toTable.equals(that.toTable);
    }

    @Override
    public int hashCode() {
        int result = fromTable.hashCode();
        result = 31 * result + fromColumn.hashCode();
        result = 31 * result + toTable.hashCode();
        result = 31 * result + toColumn.hashCode();
        return result;
    }
}
