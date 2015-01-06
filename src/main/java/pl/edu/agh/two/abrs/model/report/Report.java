package pl.edu.agh.two.abrs.model.report;

import java.util.List;

public class Report {

    private final String name;

    private final List<ReportElement> elements;

    public Report(String name, List<ReportElement> elements) {
        this.name = name;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public List<ReportElement> getElements() {
        return elements;
    }
}
