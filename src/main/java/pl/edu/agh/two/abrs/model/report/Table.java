package pl.edu.agh.two.abrs.model.report;

import com.itextpdf.text.Element;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.service.export.RendererVisitor;

import java.util.List;

public class Table extends ReportElement {

    private final String type = "TABLE";

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

    @Override
    public Element render(RendererVisitor renderer) {
        return renderer.render(this);
    }

    @Override
    public String getType() {
        return type;
    }
}
