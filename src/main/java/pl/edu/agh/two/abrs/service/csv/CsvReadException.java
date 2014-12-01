package pl.edu.agh.two.abrs.service.csv;

public class CsvReadException extends  Exception{

    public CsvReadException(String message, Throwable t){
        super(message, t);
    }
}
