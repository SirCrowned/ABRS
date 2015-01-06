package pl.edu.agh.two.abrs.service.export;

import pl.edu.agh.two.abrs.model.report.Report;

import java.io.File;

public interface ExporterService {

    /**
     * Exports Report to PDF file. The following images will be rendered from
     * report elements depending on the element type:
     * <p>
     * Table            -   a cellular table will be drawn.
     * All values and headers will be presented as Strings.
     * <p>
     * ChartType.LINE   -   requires ChartData pairs to be values (x, y) convertible to Double
     * <p>
     * ChartType.BAR    -   requires the first elements of the ChartData pair to be Number
     * and the second elements of the ChartData pairs to be String
     * <p>
     * ChartType.PIE    -   requires the first elements of the ChartData pair to be String
     * and the second elements of the ChartData pairs to be Number
     *
     * @param report report to export
     * @return PDF file containing all report elements
     * @throws ExporterException when report cannot be exported
     */
    File exportReport(Report report) throws ExporterException;
}
