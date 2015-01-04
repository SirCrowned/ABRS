package pl.edu.agh.two.abrs;

public class RowItem {

    private String columnLabel;

    private Object value;

    public RowItem(String columnLabel, Object value) {
        this.columnLabel = columnLabel;
        this.value = value;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
