package pl.edu.agh.two.abrs.service.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.report.Report;
import pl.edu.agh.two.abrs.model.report.ReportElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExporterImpl implements ExporterService {

    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 36, Font.BOLD);

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public File exportReport(Report report) throws ExporterException {
        File f;
        FileOutputStream out;
        Document document = new Document();

        try {
            f = File.createTempFile("tmpDirectory", ".pdf");
            f.deleteOnExit();
            out = new FileOutputStream(f);

            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            document.addTitle(report.getName());
            Paragraph preface = new Paragraph();
            preface.add(new Paragraph(report.getName(), titleFont));
            addEmptyLine(preface, 1);
            document.add(preface);
            for (ReportElement reportElement : report.getElements()) {
                Element image = createImage(reportElement);
                document.add(image);
            }
            document.close();
        } catch (IOException e) {
            throw new ExporterException("Cannot create temporary file", e);
        } catch (DocumentException e) {
            throw new ExporterException("Cannot export PDF document", e);
        }
        return f;
    }

    private Element createImage(ReportElement reportElement) throws ExporterException {
        Element element;

        try {
            RendererVisitor renderer = new RendererVisitor();
            element = reportElement.render(renderer);
        } catch (Exception e) {
            throw new ExporterException("Cannot create report element image", e);
        }

        return element;
    }
}

