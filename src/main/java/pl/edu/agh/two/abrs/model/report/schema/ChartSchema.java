package pl.edu.agh.two.abrs.model.report.schema;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChartSchema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(value = EnumType.STRING)
	private ChartType type;

	private String table;

	private String xAxisColumn;

	private String yAxisColumn;

	public ChartSchema() {
	}

	public ChartSchema(String name, ChartType type, String table, String xAxisColumn, String yAxisColumn) {
		this.name = name;
		this.type = type;
		this.table = table;
		this.xAxisColumn = xAxisColumn;
		this.yAxisColumn = yAxisColumn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChartType getType() {
		return type;
	}

	public void setType(ChartType type) {
		this.type = type;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getxAxisColumn() {
		return xAxisColumn;
	}

	public void setxAxisColumn(String xAxisColumn) {
		this.xAxisColumn = xAxisColumn;
	}

	public String getyAxisColumn() {
		return yAxisColumn;
	}

	public void setyAxisColumn(String yAxisColumn) {
		this.yAxisColumn = yAxisColumn;
	}
}
