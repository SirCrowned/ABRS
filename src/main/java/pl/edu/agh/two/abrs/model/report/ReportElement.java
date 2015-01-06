package pl.edu.agh.two.abrs.model.report;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import pl.edu.agh.two.abrs.service.export.RendererVisitor;

import java.io.IOException;

public abstract class ReportElement {
    public abstract Element render(RendererVisitor renderer) throws IOException, BadElementException;

    public abstract String getType();
}
