package pl.edu.agh.two.abrs.model.report;

import pl.edu.agh.two.abrs.Row;

import java.util.List;

public class Table extends ReportElement {

    private final String name;

    private final Row header;

    private final List<Row> rows;

    public Table(String name, List<Row> rows, Row header) {
        this.name = name;
        this.rows = rows;
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public Row getHeader() {
        return header;
    }

    public List<Row> getRows() {
        return rows;
    }
}
