package pl.edu.agh.two.abrs.service.db;

public class DbReaderException extends Exception {
    public DbReaderException(Exception e) {
        super(e.getMessage(), e);
    }

    public DbReaderException(String message) {
        super(message);
    }

    public DbReaderException(String message, Exception e) {
        super(message, e);
    }
}
