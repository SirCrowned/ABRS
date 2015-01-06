package pl.edu.agh.two.abrs.service.export;

public class ExporterException extends Exception {
    public ExporterException(Exception e) {
        super(e.getMessage(), e);
    }

    public ExporterException(String message) {
        super(message);
    }

    public ExporterException(String message, Exception e) {
        super(message, e);
    }
}
